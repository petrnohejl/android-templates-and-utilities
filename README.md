Android Templates and Utilities [DEPRECATED]
============================================

*Android Templates and Utilities are deprecated. I started with this project in 2012. Android ecosystem has changed a lot since then. There is a new programming language Kotlin, Android Jetpack components, RxJava and other technologies. These templates are written in Java and are not up to date.*

Android Templates and Utilities is a collection of source codes, utilities, templates and snippets for Android development. It helps to build new apps and shows the best way how to implement certain things on Android platform.

I write these templates for my personal need. I use it almost every day for developing apps. It is something like cookbook for me. All my apps are based on these templates.

Note that all reusable utilities and classes have been moved to a new standalone library [Alfonz](https://github.com/petrnohejl/Alfonz). These templates use [Alfonz](https://github.com/petrnohejl/Alfonz) as a dependency.


Content
-------

In this repo you can find templates for styling app, handling activity result, working with alarm manager, animations, sending and receiving broadcasts, using dual pane layout, loading and caching images, working with intents, map, navigation drawer, showing notifications, working with parcelable, preferences, running service, setting SSL connection, timer, working with view pager, webview and much more. All templates were tested and should be compatible with Android 4.1 (API level 16).

Android Templates and Utilities are organized into 3 groups:

* Base - basic skeleton of the Android project
* Res - resource code including xml files, images
* Src - source code including classes, resources


Usage
-----

How to use these templates? Just copy a template into your project, rename package "com.example" to your own package name, rename classes' or resource's names if you want and customize the code to your needs. Some templates have /src/fragment/ExampleFragment.java class which demonstrates how to use the template in a Fragment.


Example
-------

Example of creating a basic app skeleton with 1 Activity and 1 Fragment via Android Studio:

* Create a new Android project with package name "com.example"
* Copy & paste Base
* Copy & paste Res-Placeholder
* Copy & paste Res-Strings
* Copy & paste Res-Theme
* Copy & paste Src-Application-Class
* Copy & paste Src-Config
* Copy & paste Src-MVC-Activity
* Copy & paste Src-MVC-Fragment
* Add [Alfonz](https://github.com/petrnohejl/Alfonz) dependencies in /mobile/build.gradle
* Initialize Logcat utility inside ExampleApplication.onCreate(): `Logcat.init(ExampleConfig.LOGS, "EXAMPLE");`
* Open ExampleActivity.java and comment out onCreateOptionsMenu(...) and onOptionsItemSelected(...) methods
* Build the project and voilà, we have a basic MVC skeleton for our future app


Developed by
------------

[Petr Nohejl](http://petrnohejl.cz)


License
-------

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
