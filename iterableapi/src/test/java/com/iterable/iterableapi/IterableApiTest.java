package com.iterable.iterableapi;

import android.net.Uri;

import com.iterable.iterableapi.unit.BaseTest;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@PrepareForTest(IterableUtil.class)
public class IterableApiTest extends BaseTest {

    private MockWebServer server;

    @Before
    public void setUp() {
        server = new MockWebServer();
        IterableApi.overrideURLEndpointPath(server.url("").toString());
        IterableApi.sharedInstance = new IterableApi();
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
        server = null;
    }

    @Test
    public void testSdkInitializedWithoutEmailOrUserId() throws Exception {
        IterableApi.initialize(RuntimeEnvironment.application, "apiKey");
        IterableApi.getInstance().setEmail(null);

        // Verify that none of the calls to the API result in a request
        IterableApi.getInstance().track("testEvent");
        IterableApi.getInstance().trackInAppOpen("12345");
        IterableApi.getInstance().inAppConsume("12345");
        IterableApi.getInstance().trackInAppClick("12345", "");
        IterableApi.getInstance().registerDeviceToken("12345");
        IterableApi.getInstance().disablePush("12345", "12345");
        IterableApi.getInstance().updateUser(new JSONObject());
        IterableApi.getInstance().updateEmail("");
        IterableApi.getInstance().trackPurchase(10.0, new ArrayList<CommerceItem>());

        RecordedRequest request = server.takeRequest(100, TimeUnit.MILLISECONDS);
        assertNull(request);
    }

    @Test
    public void testEmailUserIdPersistence() throws Exception {
        IterableApi.initialize(RuntimeEnvironment.application, "apiKey");
        IterableApi.getInstance().setEmail("test@email.com");

        IterableApi.sharedInstance = new IterableApi();
        IterableApi.initialize(RuntimeEnvironment.application, "apiKey");
        assertEquals("test@email.com", IterableApi.getInstance().getEmail());
        assertNull(IterableApi.getInstance().getUserId());

        IterableApi.getInstance().setUserId("testUserId");
        IterableApi.sharedInstance = new IterableApi();
        IterableApi.initialize(RuntimeEnvironment.application, "apiKey");
        assertEquals("testUserId", IterableApi.getInstance().getUserId());
        assertNull(IterableApi.getInstance().getEmail());
    }

    @Test
    public void testAttributionInfoPersistence() throws Exception {
        IterableApi.initialize(RuntimeEnvironment.application, "apiKey");

        IterableAttributionInfo attributionInfo = new IterableAttributionInfo(1234, 4321, "message");
        IterableApi.getInstance().setAttributionInfo(attributionInfo);

        PowerMockito.spy(IterableUtil.class);

        // 23 hours, not expired, still present
        when(IterableUtil.currentTimeMillis()).thenReturn(System.currentTimeMillis() + 3600 * 23 * 1000);
        IterableAttributionInfo storedAttributionInfo = IterableApi.getInstance().getAttributionInfo();
        assertNotNull(storedAttributionInfo);
        assertEquals(attributionInfo.campaignId, storedAttributionInfo.campaignId);
        assertEquals(attributionInfo.templateId, storedAttributionInfo.templateId);
        assertEquals(attributionInfo.messageId, storedAttributionInfo.messageId);

        // 24 hours, expired, attributionInfo should be null
        when(IterableUtil.currentTimeMillis()).thenReturn(System.currentTimeMillis() + 3600 * 24 * 1000);
        storedAttributionInfo = IterableApi.getInstance().getAttributionInfo();
        assertNull(storedAttributionInfo);

        PowerMockito.doCallRealMethod().when(IterableUtil.class, "currentTimeMillis");
    }

    @Test
    public void testHandleUniversalLinkRewrite() throws Exception {
        IterableUrlHandler urlHandlerMock = mock(IterableUrlHandler.class);
        when(urlHandlerMock.handleIterableURL(any(Uri.class), any(IterableActionContext.class))).thenReturn(true);
        IterableApi.initialize(RuntimeEnvironment.application, "fake_key", new IterableConfig.Builder().setUrlHandler(urlHandlerMock).build());

        String url = "https://links.iterable.com/api/docs#!/email";
        IterableApi.handleAppLink("http://links.iterable.com/a/60402396fbd5433eb35397b47ab2fb83?_e=joneng%40iterable.com&_m=93125f33ba814b13a882358f8e0852e0");

        ArgumentCaptor<Uri> capturedUri = ArgumentCaptor.forClass(Uri.class);
        ArgumentCaptor<IterableActionContext> capturedActionContext = ArgumentCaptor.forClass(IterableActionContext.class);
        verify(urlHandlerMock, timeout(5000)).handleIterableURL(capturedUri.capture(), capturedActionContext.capture());
        assertEquals(url, capturedUri.getValue().toString());
        assertEquals(IterableActionSource.APP_LINK, capturedActionContext.getValue().source);
        assertTrue(capturedActionContext.getValue().action.isOfType(IterableAction.ACTION_TYPE_OPEN_URL));
        assertEquals(url, capturedActionContext.getValue().action.getData());
    }

}
