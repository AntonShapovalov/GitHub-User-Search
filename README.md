# GitHub Users Search

Android application to search users via GitHub API. Project demonstrates `how-to` use Kotlin, Android Architecture Components, Dagger and RxJava to perform remote API calls and cache data to the local DB.

#### Structure

Application contains three screens:

1. Splash screen - shows logo and inits Dagger components in parallel.
2. Search screen - performs user search via GitHub API, saves result to the local DB and displays the result as a list. 
3. Details screen - shows user name, bio and repositories list.

#### How to install

Please use [app-release.apk](./app/release/) file.

#### Used language and libraries
 * [Kotlin](https://kotlinlang.org/docs/tutorials/kotlin-android.html) - primary project language.
 * [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) - the core of [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) pattern.
 * [RxJava](https://github.com/ReactiveX/RxJava) - simple way to manage data chains.
 * [Dagger](https://google.github.io/dagger/) - dependency injection framework.
 * [Retrofit](http://square.github.io/retrofit/) - to perform API call.
 * [GreenDao](http://greenrobot.org/greendao/) - ORM, to cache data in local SQLite DB.
