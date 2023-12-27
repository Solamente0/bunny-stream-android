# Bunny Stream Android SDK

https://bunny.net/

SDK includes Bunny API client, Video Player and streaming implementation

## Libraries

**OpenAPI code generator** - [Kotlin + Retrofit](https://openapi-generator.tech/docs/generators/kotlin/)

**Media3 ExoPlayer** - [Video Player backend](https://developer.android.com/guide/topics/media/exoplayer/)

**RootEncoder** - [Streaming library](https://github.com/pedroSG94/RootEncoder)

## Configuration

Before using the core SDK, player or streaming SDK first initialize the SDK by calling:

```kotlin
BunnyStreamSdk.initialize(context, accessKey, libraryId)
```
You can find `accessKey`, and `libraryId` on the [bunny.net dashboard](dash.bunny.net).

## Player

To use player in Jetpack Compose, instantiate `BunnyVideoPlayer` in `AndroidView` and call `playVideo(libraryId, videoId)`.

You can find an example in [PlayerScreen.kt](app/src/main/java/net/bunnystream/android/player/PlayerScreen.kt).

To use player in XML views, add `BunnyVideoPlayer` widget in layout and call `playVideo(videoId)` after layout is inflated.

You can supply your own icons for player by setting `iconSet` field. Check [PlayerIconSet](player/src/main/java/net/bunnystream/player/model/PlayerIconSet.kt).

## Recording

Use [BunnyRecordingView](recording/src/main/java/net/bunnystream/recording/BunnyRecordingView.kt) to implement video recording. 

You can use default UI or use your own by instantiating `BunnyStreamView` in `Box` (Jetpack Compose) or `FrameLayout` (XML) and setting `hideDefaultControls` to `true` or `app:brvHideDefaultControls="true"` in XML.

If you opt for your own UI, set `recordingStateListener` see [RecordingStateListener](recording/src/main/java/net/bunnystream/recording/RecordingStateListener.kt) and `recordingDurationListener`, see [RecordingDurationListener](recording/src/main/java/net/bunnystream/recording/RecordingDurationListener.kt) to get events and update UI accordingly.

See [RecordingActivity](app/src/main/java/net/bunnystream/android/ui/recording/RecordingActivity.kt) as an example of usage.

## Other examples

Check `app` module for examples for other SDK usages and file uploading.

## Documentation

To (re)generate documentation in GFMD foramt, run `./gradlew dokkaGfmMultimodule`. Documentation will be available in `docs` folder in project root.
 
