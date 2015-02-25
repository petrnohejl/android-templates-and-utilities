Example
=======

*Fill in text about the app.*


Features
========

*Fill in text about main features.*


Installation
============

*Fill in text about installation.*


Usage
=====

*Fill in text about usage.*


Documentation
=============

*Fill in text about documentation.*


Changelog
=========

*Fill in text about version changes.*


Building project
================

This chapter describes how to build APK with Gradle and prepare app for publishing.

You don't need to install Gradle on your system, because there is a [Gradle Wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html). The wrapper is a batch script on Windows, and a shell script for other operating systems. When you start a Gradle build via the wrapper, Gradle will be automatically downloaded and used to run the build.

1. Clone this repository
2. Open configuration file _/mobile/src/main/java/com/example/ExampleConfig.java_ and set constants as required (see below for more info)
3. Open main build script _/mobile/build.gradle_ and set constants and config fields as required (see below for more info)
4. Run `gradlew assemble` in console
5. APK should be available in _/mobile/build/outputs/apk_ directory

**Note:** You will also need a "local.properties" file to set the location of the SDK in the same way that the existing SDK requires, using the "sdk.dir" property. Example of "local.properties" on Windows: `sdk.dir=C:\\adt-bundle-windows\\sdk`. Alternatively, you can set an environment variable called "ANDROID\_HOME".

**Tip:** Command `gradlew assemble` builds both - debug and release APK. You can use `gradlew assembleDebug` to build debug APK. You can use `gradlew assembleRelease` to build release APK. Debug APK is signed by debug keystore. Release APK is signed by own keystore, stored in _/extras/keystore_ directory.

**Signing process:** Keystore passwords are automatically loaded from property file during building the release APK. Path to this file is defined in "keystore.properties" property in "gradle.properties" file. If this property or the file does not exist, user is asked for passwords explicitly.


ExampleConfig.java
------------------

This is the main configuration file and there are some important constants: addresses to API endpoints, API keys to 3rd party services etc. Make sure that all constants are set up properly.


build.gradle
------------

This is the main build script and there are 4 important constants for defining version code and version name.

* VERSION\_MAJOR
* VERSION\_MINOR
* VERSION\_PATCH
* VERSION\_BUILD

See [Versioning Your Applications](http://developer.android.com/tools/publishing/versioning.html#appversioning) in Android documentation for more info.

There are also a build config fields in this script. Check "buildTypes" configuration and make sure that all fields are set up properly for debug and release. It is very important to correctly set these true/false switches before building the APK.

* LOGS - true for showing logs
* DEV\_API - true for development API endpoint

**Important:** Following configuration should be used for release build type, intended for publishing on Google Play:

```groovy
buildConfigField "boolean", "LOGS", "false"
buildConfigField "boolean", "DEV_API", "false"
``` 


Dependencies
============

* [ActionBar-PullToRefresh](https://github.com/chrisbanes/ActionBar-PullToRefresh)
* [ActionBarSherlock](http://actionbarsherlock.com/)
* [ActiveAndroid](http://www.activeandroid.com/)
* [AndEngine](http://www.andengine.org/)
* [Android Support Library v4](http://developer.android.com/tools/extras/support-library.html)
* [Androrm](http://www.androrm.com/)
* [AppCompat](https://developer.android.com/reference/android/support/v7/appcompat/package-summary.html)
* [Crittercism](http://www.crittercism.com)
* [Facebook Android SDK](https://github.com/facebook/facebook-android-sdk)
* [File Picker Activity](https://github.com/Kaloer/Android-File-Picker-Activity)
* [Flurry](http://www.flurry.com/)
* [Google Play Services](http://developer.android.com/google/play-services/index.html)
* [GreenDAO](http://greendao-orm.com/)
* [GSON](http://code.google.com/p/google-gson/)
* [Holo Everywhere](https://github.com/Prototik/HoloEverywhere)
* [Ignition](https://github.com/kaeppler/ignition)
* [Jackson](http://jackson.codehaus.org/)
* [JazzyViewPager](https://github.com/jfeinstein10/JazzyViewPager)
* [ListViewAnimations](https://github.com/nhaarman/ListViewAnimations)
* [Localytics](http://www.localytics.com/)
* [Nine Old Androids](https://github.com/JakeWharton/NineOldAndroids)
* [Number Picker](https://github.com/novak/numpicker-demo)
* [OkHttp](https://github.com/square/okhttp)
* [Orman](https://github.com/ahmetalpbalkan/orman)
* [OrmLite](http://ormlite.com/)
* [Otto](https://github.com/square/otto)
* [PhotoView](https://github.com/chrisbanes/PhotoView)
* [Picasso](https://github.com/square/picasso)
* [Polaris2](https://github.com/cyrilmottier/Polaris2)
* [PreferenceFragment](https://github.com/kolavar/android-support-v4-preferencefragment)
* [Pubnub](https://github.com/pubnub/java/tree/master/android)
* [Retrofit](https://github.com/square/retrofit)
* [RobotoTextView](https://github.com/johnkil/Android-RobotoTextView)
* [RSS Reader](https://github.com/matshofman/Android-RSS-Reader-Library)
* [ShowcaseView](https://github.com/Espiandev/ShowcaseView)
* [Simple XML](http://simple.sourceforge.net/)
* [Sliding Menu](https://github.com/jfeinstein10/SlidingMenu)
* [StackBlur](https://github.com/kikoso/android-stackblur)
* [Sticky List Headers](https://github.com/emilsjolander/StickyListHeaders)
* [Styled Dialogs](https://github.com/inmite/android-styled-dialogs)
* [Sugar ORM](http://satyan.github.com/sugar/index.html)
* [Swipe To Dismiss](https://github.com/JakeWharton/SwipeToDismissNOA)
* [Tree View List](https://github.com/Polidea/tree-view-list-android)
* [Twitter4J](http://twitter4j.org/en/)
* [UndoBar](https://github.com/soarcn/UndoBar)
* [Universal Image Loader](https://github.com/nostra13/Android-Universal-Image-Loader)
* [View Pager Indicator](https://github.com/JakeWharton/Android-ViewPagerIndicator)
* [Volley](https://android.googlesource.com/platform/frameworks/volley/)
* [ZXing](http://code.google.com/p/zxing/)


Testing
=======

* Test app on different Android versions (handset, tablet)
* Test overdraws
* Test offline/empty/progress states
* Test migration from old version to the new one
* Test slow internet connection
* Test reboot (boot receivers, alarms, services)
* Test analytics tracking
* Monkey test (fast clicking, changing orientation)


Publishing
==========

* Set proper versions in the main build script
* Check build config fields in the main build script
* Update text info in changelog/about/help
* Add analytics events for new features
* Set Android key hash on developers.facebook.com


Developed by
============

[Petr Nohejl](http://petrnohejl.cz)


License
=======

    Copyright 2012 Petr Nohejl

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
