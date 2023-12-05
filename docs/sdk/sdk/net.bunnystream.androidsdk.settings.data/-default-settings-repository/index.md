//[sdk](../../../index.md)/[net.bunnystream.androidsdk.settings.data](../index.md)/[DefaultSettingsRepository](index.md)

# DefaultSettingsRepository

[androidJvm]\
class [DefaultSettingsRepository](index.md)(httpClient: HttpClient, coroutineDispatcher: CoroutineDispatcher) : [SettingsRepository](../../net.bunnystream.androidsdk.settings.domain/-settings-repository/index.md)

## Constructors

| | |
|---|---|
| [DefaultSettingsRepository](-default-settings-repository.md) | [androidJvm]<br>constructor(httpClient: HttpClient, coroutineDispatcher: CoroutineDispatcher) |

## Functions

| Name | Summary |
|---|---|
| [fetchSettings](fetch-settings.md) | [androidJvm]<br>open suspend override fun [fetchSettings](fetch-settings.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Either&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [PlayerSettings](../../net.bunnystream.androidsdk.settings.domain.model/-player-settings/index.md)&gt; |
