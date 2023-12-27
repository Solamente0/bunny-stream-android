//[player](../../../index.md)/[net.bunnystream.player](../index.md)/[DefaultBunnyPlayer](index.md)

# DefaultBunnyPlayer

[androidJvm]\
class [DefaultBunnyPlayer](index.md) : [BunnyPlayer](../../net.bunnystream.player.common/-bunny-player/index.md)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [autoPaused](auto-paused.md) | [androidJvm]<br>open override var [autoPaused](auto-paused.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [currentPlayer](current-player.md) | [androidJvm]<br>open override var [currentPlayer](current-player.md): [Player](https://developer.android.com/reference/kotlin/androidx/media3/common/Player.html)? |
| [playerSettings](player-settings.md) | [androidJvm]<br>open override var [playerSettings](player-settings.md): [PlayerSettings](../../../../sdk/sdk/net.bunnystream.androidsdk.settings.domain.model/-player-settings/index.md)? |
| [playerStateListener](player-state-listener.md) | [androidJvm]<br>open override var [playerStateListener](player-state-listener.md): [PlayerStateListener](../-player-state-listener/index.md)? |
| [seekThumbnail](seek-thumbnail.md) | [androidJvm]<br>open override var [seekThumbnail](seek-thumbnail.md): [SeekThumbnail](../../net.bunnystream.player.model/-seek-thumbnail/index.md)? |

## Functions

| Name | Summary |
|---|---|
| [areSubtitlesEnabled](are-subtitles-enabled.md) | [androidJvm]<br>open override fun [areSubtitlesEnabled](are-subtitles-enabled.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getCurrentPosition](get-current-position.md) | [androidJvm]<br>open override fun [getCurrentPosition](get-current-position.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getDuration](get-duration.md) | [androidJvm]<br>open override fun [getDuration](get-duration.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getPlaybackSpeeds](get-playback-speeds.md) | [androidJvm]<br>open override fun [getPlaybackSpeeds](get-playback-speeds.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt; |
| [getSpeed](get-speed.md) | [androidJvm]<br>open override fun [getSpeed](get-speed.md)(): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [getSubtitles](get-subtitles.md) | [androidJvm]<br>open override fun [getSubtitles](get-subtitles.md)(): [Subtitles](../../net.bunnystream.player.model/-subtitles/index.md) |
| [getVideoQualityOptions](get-video-quality-options.md) | [androidJvm]<br>open override fun [getVideoQualityOptions](get-video-quality-options.md)(): [VideoQualityOptions](../../net.bunnystream.player.model/-video-quality-options/index.md)? |
| [getVolume](get-volume.md) | [androidJvm]<br>open override fun [getVolume](get-volume.md)(): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [isMuted](is-muted.md) | [androidJvm]<br>open override fun [isMuted](is-muted.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isPlaying](is-playing.md) | [androidJvm]<br>open override fun [isPlaying](is-playing.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [mute](mute.md) | [androidJvm]<br>open override fun [mute](mute.md)() |
| [pause](pause.md) | [androidJvm]<br>open override fun [pause](pause.md)(autoPaused: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [play](play.md) | [androidJvm]<br>open override fun [play](play.md)() |
| [playVideo](play-video.md) | [androidJvm]<br>open override fun [playVideo](play-video.md)(playerView: [PlayerView](https://developer.android.com/reference/kotlin/androidx/media3/ui/PlayerView.html), video: VideoModel, retentionData: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, playerSettings: [PlayerSettings](../../../../sdk/sdk/net.bunnystream.androidsdk.settings.domain.model/-player-settings/index.md)) |
| [release](release.md) | [androidJvm]<br>open override fun [release](release.md)() |
| [replay](replay.md) | [androidJvm]<br>open override fun [replay](replay.md)() |
| [seekTo](seek-to.md) | [androidJvm]<br>open override fun [seekTo](seek-to.md)(positionMs: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [selectQuality](select-quality.md) | [androidJvm]<br>open override fun [selectQuality](select-quality.md)(quality: [VideoQuality](../../net.bunnystream.player.model/-video-quality/index.md)) |
| [selectSubtitle](select-subtitle.md) | [androidJvm]<br>open override fun [selectSubtitle](select-subtitle.md)(subtitleInfo: [SubtitleInfo](../../net.bunnystream.player.model/-subtitle-info/index.md)) |
| [setSpeed](set-speed.md) | [androidJvm]<br>open override fun [setSpeed](set-speed.md)(speed: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) |
| [setSubtitlesEnabled](set-subtitles-enabled.md) | [androidJvm]<br>open override fun [setSubtitlesEnabled](set-subtitles-enabled.md)(enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setVolume](set-volume.md) | [androidJvm]<br>open override fun [setVolume](set-volume.md)(volume: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) |
| [skipForward](skip-forward.md) | [androidJvm]<br>open override fun [skipForward](skip-forward.md)() |
| [stop](stop.md) | [androidJvm]<br>open override fun [stop](stop.md)() |
| [unmute](unmute.md) | [androidJvm]<br>open override fun [unmute](unmute.md)() |
