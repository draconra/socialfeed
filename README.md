# Social Feed App

This is repository for **Social Feed** Android App Version **1.0**. The app was developed in
- Kotlin
- Android Min SDK: 21 (Lolipop)
- Android Target SDK: 30 //Follow the minimum SDK target standards set by **Google**.

Git Branch
- `main`: production
- `develop`: developer playground
- `feature/XYZ`: new feature development

For development flow we are following gitflow (https://datasift.github.io/gitflow/IntroducingGitFlow.html).

When start to work on new feature, you need to checkout new branch with prefix `feature/`.  During development you can regularly pull updates from develop branch to keep updates. After completion, you should do commit and create a **Merge Request** to develop branch.

## App Versioning
We use semantic versioning for version management. So version will be `X.Y.Z`.
- **X** is `MAJOR` changes when you make incompatible API changes,
- **Y** is `MINOR` changes when you add functionality in a backwards-compatible manner
- **Z** is `PATCH` changes when you make backwards-compatible bug fixes.

## Build Types
By default, the build system defines two buildTypes: debug and release. But in this project, buildType debug is not explicity shown but it includes debugging tools and is signed with `Build.DEBUG` key.

### Build Type release
In this project, buildType release uses several additions such as:
- `minifyEnabled true` works for enables code shrinking.
- `shrinkResources true` works for enables resources/assets shrinking.
- `proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), "proguard-rules.pro"` works for performs whole program optimizations.
- `firebaseAppDistribution { groups = "dev-android" }` works for shows the groups that will be shared if using a firebase distribution.

## Product Flavors
The productFlavors block is allow you to create different versions of your app that can override the defaultConfig block with their own settings. In this project productFlavor is divided into two:
- `dev`: Used when the developer wants to play on staging.
- `prod`: Used when the developer wants to play on production.

## Project Structure
Globally the project has the following top level packages:
1. **data**: Contains models and all the data accessing and manipulating components.
2. **application**: Contain Application Class.
3. **repository**: Modules handle data operations. They provide a clean API so that the rest of the app can retrieve this data easily.
3. **ui**: Contains the classes View (Activity, Fragment), Adapter.
4. **viewmodel**: Provides the data for a specific UI component, such as a fragment or activity, and contains data-handling business logic to communicate with the model.
5. **util**: Contains Utility & Helper classes.

## Release / Deployment

Before release app to Play store you should ensure that all base code was merged to master branch and build new release from this branch.
It's recommended to make a `git tag` for current release version.

Keystore is placed in `config` directory. You can find the credentials for apk signing inside `credentials.txt`
You can build release apk:
- Go to `Build` > `Generate Signed APK`
- Follow the dialog and choose the **keystore**
- Wait until it's done.

## Architecture
This project use MVVM (Model-View-ViewModel) Architecture

### Libraries & Tools
The following is a collection of libraries used in this project:

#### Google Libs
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Pagination](https://developer.android.com/topic/libraries/architecture/paging) - The Paging Library helps you load and display small chunks of data at a time. Loading partial data on demand reduces usage of network bandwidth and system resources.
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app.

#### Network Libs
* [Retrofit](https://square.github.io/retrofit/) - Used to perform HTTP connection
* [OkHttp](https://github.com/square/okhttp) - Http client to be used along with retrofit
* [Gson](https://github.com/google/gson) - Used along with retrofit to perform serialization/deserialization of API response

#### Reactive Libs
* [RxJava](https://github.com/ReactiveX/RxJava%5D%28https://github.com/ReactiveX/RxJava) - Used to do async process nicely like chain async operations. We have basic usage of RxJava2 in API call.
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) - RxJava bindings for Android
* [RxBinding](https://github.com/JakeWharton/RxBinding) - RxJava binding APIs for Android's UI widgets
* [RxPermission](https://github.com/tbruyelle/RxPermissions) - Android runtime permissions powered by RxJava2

#### Utility Code Libs
* [Exoplayer](https://developer.android.com/guide/topics/media/exoplayer) - ExoPlayer supports features like Dynamic adaptive streaming over HTTP (DASH), SmoothStreaming and Common Encryption, which are not supported by MediaPlayer. It's designed to be easy to customize and extend.

#### Utility UI Libs
* [Glide](https://github.com/bumptech/glide) - Image loader and caching.

### New Feature Guidance

- [ ] If there’s new feature with new API, create feature package inside `ui ` package, consisting of **view, presenter, and  courseInteractor**. Start to write classes of each of this package. Each of this package contains abstraction class and its implementation
- [ ] Add API Endpoint in `ApiEndpoint.kt` class. Then, create request and response model of this particular API inside `data/network/request` or `data/network/response`
- [ ] Create API access function inside `SocialFeedApi.kt` and just start implement it inside courseInteractor class.
- [ ] Access API from view to courseInteractor using viewmodel
- [ ] Test and looks how it works
- [ ] Raise a Merge Request to develop branch


## Technical Debts
- Lack of testings

## Credits
- **Mohamad Aditya Sumardi** - aditya.sumardi@gmail.com
