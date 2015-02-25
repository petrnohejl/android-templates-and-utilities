Android Templates And Utilities
===============================

Android Templates And Utilities is a collection of source codes, utilities, 
templates and snippets for Android development. It helps to build new apps and 
shows the best way how to implement certain problems on Android platform.

I write these templates for my personal need. I use it almost every day
for developing apps. It is something like cookbook for me. All my apps are
based on these templates.

In this repo you can find templates for styling action bar, working with alarm
manager, communicating with server API, sending and receiving broadcasts,
working with database, creating dialogs, using dual pane layout, fragments,
GCM, geolocation, loading and caching images,
working with intents, map, navigation drawer, showing notifications,
preferences, running service, setting SSL connection, working with view pager,
webview and much more. All templates were tested and should be compatible
with Android 4+.

Android Templates And Utilities are organized into 3 groups:

* Base - basic skelet of the Android project
* Res - resource code including xml files, images
* Src - source code including classes, resources


Usage
=====

How to use these templates? Just copy the template into your project, rename
package "com.example" to your own package name, rename classes' or resource's
names if you want and customize the code to your needs. Some templates have
/src/fragment/ExampleFragment.java class which demonstrates how to use
the template in a Fragment.


Example
=======

Example of creating a basic app skelet with 1 Activity and 1 Fragment via Android Studio:

* Create a new Android project with package name "com.example"
* Copy & paste Base
* Copy & paste Res-Placeholder
* Copy & paste Res-Strings
* Copy & paste Res-Theme
* Copy & paste Src-Activity
* Copy & paste Src-Application-Class
* Copy & paste Src-Config
* Copy & paste Src-Fragment
* Copy & paste Src-Logcat
* Copy & paste Src-NetworkManager
* Open ExampleActivity.java and comment out onCreateOptionsMenu(...) and onOptionsItemSelected(...) methods
* Build the project and voilà, we have a basic skeleton for our future app


Developed by
============

[Petr Nohejl](http://petrnohejl.cz)

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=B3JBY3NU6L2XY)


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
