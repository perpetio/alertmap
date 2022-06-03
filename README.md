# AlertApp

App to inform about air raid alerts in Ukraine

## Features include:
* display the current state of air alarms in the color map form
* display the map as a separate widget on home screen
* monitor the required districts
* notify user about districts state changes
* provide flexible app settings

## Preview
<p align="center">
    <img src="assets/main_screen.png" width='170'/>
    <img src="assets/select_states_screen.png" width='170'/>
    <img src="assets/widget.png" width='170'/>
    <img src="assets/notification.png" width='170'/>
</p>

## Video preview
<img src="assets/video_preview.gif" width='300'/>

## Used libraries:
* [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started?authuser=3) - framework for navigating between 'destinations' within an Android application. Navigation provides a consistent API whether destinations are implemented as Fragments, Activities, or other components.
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) - a coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
* [Retrofit](https://square.github.io/retrofit/) - is a REST client for Android, that makes easy to get and load JSON (or other structured data) through a REST based web service.
* [Swiperefreshlayout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout) - a UI component that handle gesture "Pull to Refresh".

## Getting started
1. Clone the repo
```
 $ git clone https://github.com/perpetio/alertmap.git
 ```
2. Open project in Android studio or IntelliJ IDEA
3. Run on android device using your IDE's tools.