# Market_Simplified - Android app
App demonstrating displaying public repositories from GitHub Api.

### Add dependency

```
// use the latest available version
implementation 'com.google.android.material:material:1.2.1'
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'androidx.recyclerview:recyclerview:1.1.0'
implementation("com.github.bumptech.glide:glide:4.11.0@aar") {
    transitive = true
}
```

### Enable Java 8 support

You also need to ensure Java 8 support is enabled by adding the following block to each of your app module's `build.gradle` file inside the `android` block:

```
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

### Screenshots
<img src="https://github.com/u-dilipan/Market_Simplified/blob/master/image1.jpg" width="250" height="400">
<img src="https://github.com/u-dilipan/Market_Simplified/blob/master/image2.jpg" width="250" height="400">
<img src="https://github.com/u-dilipan/Market_Simplified/blob/master/image3.jpg" width="250" height="400">
