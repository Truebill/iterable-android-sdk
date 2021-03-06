# Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased]
#### Added
- nothing yet

#### Removed
- nothing yet

#### Changed
- nothing yet

#### Fixed
- nothing yet

## [2.2.5](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.2.5)
 _Released on 2017-03-31_
 
#### Changed
- Updated requests to not send when there is an exception while constructing the JSON request body.
 
#### Fixed
- Fixed the reference to internal fields in NotificationCompat.Builder for buildVersion 27.

## [2.2.4](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.2.4)
 _Released on 2017-03-07_
 
#### Fixed
- Fixed the load sequence for retrieving a notification image.

## [2.2.3](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.2.3)
 _Released on 2018-01-22_
 
#### Added
- Added non-empty data body for notification rendering.
- Added default channel id support.

## [2.2.2](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.2.2)
 _Released on 2017-11-30_
 
#### Fixed
- Fixed error in IterablePushRegistration when `getDeviceToken` returns an empty PushRegistrationObject.

## [2.2.1](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.2.1)
 _Released on 2017-11-20_
 
#### Added
- Added the `updateSubscriptions` function to create to modify channel, list, and message subscription preferences.

## [2.2.0](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.2.0)
 _Released on 2017-11-03_
 
#### Added
- Added support for html based in-app notifications.

## [2.1.9](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.9)
 _Released on 2017-10-20_
 
 
#### Fixed
- Fixed payload path for image url.

## [2.1.8](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.8)
 _Released on 2017-07-28_
 
#### Added
- Added support for android image notifications.
 
#### Fixed
- Fixed load error for empty image url.

## [2.1.7](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.7)
 _Released on 2017-07-19_
 
#### Fixed
- Fixed in-app button clicks without an action defined.

## [2.1.6](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.6)
 _Released on 2017-07-19_
 
#### Added
- Added the in-app consume logic to automatically remove the notification from list of in-app notifications.

#### Fixed
- Fixed the payloadfor trackInAppClick to contain the userId.

## [2.1.5](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.5)
 _Released on 2017-06-09_
 
#### Added
- Added full support for newly created Firebase applications
- Added new functionality for `registerForPush` which takes in the optional pushServicePlatform
	- `IterableConstants.MESSAGING_PLATFORM_GOOGLE` (GCM)
	- `IterableConstants. MESSAGING_PLATFORM_FIREBASE` (FCM)
- `IterableFirebaseInstanceIDService` handles firebase token registrations automatically on install.
- Added in default tracked device values for `registerDeviceToken`

#### Changed
- Changed IterablePushRegistrationGCM to IterablePushRegistration so the registration class is not GCM specific.
- Changed the disable logic to no longer enable the deviceToken prior to disabling.

## [2.1.4](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.4)
 _Released on 2017-02-23_

#### Fixed
- fixed uploaded pom file

## [2.1.3](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.3)
 _Released on 2017-02-22_

#### Added
- Added support for Android deeplink tracking
- `getAndTrackDeeplink` tracks a click and returns the destination url.

## [2.1.2](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.2)
 _Released on 2017-01-09_

#### Changed
- Updated the PendingIntent request code to use the messageId instead of the current timestamp.

## [2.1.1](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.1)
 _Released on 2017-01-09_

#### Fixed
- fixed overwritten pushnotification metadata on subsequent notifications

## [2.1.0](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.1.0)
 _Released on 2016-12-28_

- added support for In-App Notifications with different views layouts
	- Full screen 
	- Bottom
	- Center
	- Top
- includes tracking for In-App opens and clicks
- includes support for GET requests

## [2.0.1](https://github.com/Iterable/iterable-android-sdk/releases/tag/2.0.1)
 _Released on 2016-10-13_
 
#### Added 
- Added ability to send data by userId
