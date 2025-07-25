//[player](../../../index.md)/[net.bunnystream.player.common](../index.md)/[BunnyPlayer](index.md)

# BunnyPlayer

interface [BunnyPlayer](index.md)

#### Inheritors

| |
|---|
| [DefaultBunnyPlayer](../../net.bunnystream.player/-default-bunny-player/index.md) |

## Properties

| Name | Summary |
|---|---|
| [autoPaused](auto-paused.md) | [androidJvm]<br>abstract var [autoPaused](auto-paused.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [currentPlayer](current-player.md) | [androidJvm]<br>abstract var [currentPlayer](current-player.md): [Player](https://developer.android.com/reference/kotlin/androidx/media3/common/Player.html)? |
| [playerSettings](player-settings.md) | [androidJvm]<br>abstract var [playerSettings](player-settings.md): [PlayerSettings](../../../../sdk/sdk/net.bunnystream.androidsdk.settings.domain.model/-player-settings/index.md)? |
| [playerStateListener](player-state-listener.md) | [androidJvm]<br>abstract var [playerStateListener](player-state-listener.md): [PlayerStateListener](../../net.bunnystream.player/-player-state-listener/index.md)? |
| [seekThumbnail](seek-thumbnail.md) | [androidJvm]<br>abstract var [seekThumbnail](seek-thumbnail.md): [SeekThumbnail](../../net.bunnystream.player.model/-seek-thumbnail/index.md)? |

## Functions

| Name | Summary |
|---|---|
| [areSubtitlesEnabled](are-subtitles-enabled.md) | [androidJvm]<br>abstract fun [areSubtitlesEnabled](are-subtitles-enabled.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getCurrentPosition](get-current-position.md) | [androidJvm]<br>abstract fun [getCurrentPosition](get-current-position.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getDuration](get-duration.md) | [androidJvm]<br>abstract fun [getDuration](get-duration.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getPlaybackSpeeds](get-playback-speeds.md) | [androidJvm]<br>abstract fun [getPlaybackSpeeds](get-playback-speeds.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt; |
| [getSpeed](get-speed.md) | [androidJvm]<br>abstract fun [getSpeed](get-speed.md)(): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [getSubtitles](get-subtitles.md) | [androidJvm]<br>abstract fun [getSubtitles](get-subtitles.md)(): [Subtitles](../../net.bunnystream.player.model/-subtitles/index.md) |
| [getVideoQualityOptions](get-video-quality-options.md) | [androidJvm]<br>abstract fun [getVideoQualityOptions](get-video-quality-options.md)(): [VideoQualityOptions](../../net.bunnystream.player.model/-video-quality-options/index.md)? |
| [getVolume](get-volume.md) | [androidJvm]<br>@[FloatRange](https://developer.android.com/reference/kotlin/androidx/annotation/FloatRange.html)(from = 0.0, to = 1.0)<br>abstract fun [getVolume](get-volume.md)(): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [isMuted](is-muted.md) | [androidJvm]<br>abstract fun [isMuted](is-muted.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isPlaying](is-playing.md) | [androidJvm]<br>abstract fun [isPlaying](is-playing.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [mute](mute.md) | [androidJvm]<br>abstract fun [mute](mute.md)() |
| [pause](pause.md) | [androidJvm]<br>abstract fun [pause](pause.md)(autoPaused: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [play](play.md) | [androidJvm]<br>abstract fun [play](play.md)() |
| [playVideo](play-video.md) | [androidJvm]<br>abstract fun [playVideo](play-video.md)(playerView: [PlayerView](https://developer.android.com/reference/kotlin/androidx/media3/ui/PlayerView.html), video: VideoModel, retentionData: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, playerSettings: [PlayerSettings](../../../../sdk/sdk/net.bunnystream.androidsdk.settings.domain.model/-player-settings/index.md)) |
| [release](release.md) | [androidJvm]<br>abstract fun [release](release.md)() |
| [replay](replay.md) | [androidJvm]<br>abstract fun [replay](replay.md)() |
| [seekTo](seek-to.md) | [androidJvm]<br>abstract fun [seekTo](seek-to.md)(positionMs: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [selectQuality](select-quality.md) | [androidJvm]<br>abstract fun [selectQuality](select-quality.md)(quality: [VideoQuality](../../net.bunnystream.player.model/-video-quality/index.md)) |
| [selectSubtitle](select-subtitle.md) | [androidJvm]<br>abstract fun [selectSubtitle](select-subtitle.md)(subtitleInfo: [SubtitleInfo](../../net.bunnystream.player.model/-subtitle-info/index.md)) |
| [setSpeed](set-speed.md) | [androidJvm]<br>abstract fun [setSpeed](set-speed.md)(speed: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) |
| [setSubtitlesEnabled](set-subtitles-enabled.md) | [androidJvm]<br>abstract fun [setSubtitlesEnabled](set-subtitles-enabled.md)(enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setVolume](set-volume.md) | [androidJvm]<br>abstract fun [setVolume](set-volume.md)(@[FloatRange](https://developer.android.com/reference/kotlin/androidx/annotation/FloatRange.html)(from = 0.0, to = 1.0)volume: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) |
| [skipForward](skip-forward.md) | [androidJvm]<br>abstract fun [skipForward](skip-forward.md)() |
| [stop](stop.md) | [androidJvm]<br>abstract fun [stop](stop.md)() |
| [unmute](unmute.md) | [androidJvm]<br>abstract fun [unmute](unmute.md)() |
