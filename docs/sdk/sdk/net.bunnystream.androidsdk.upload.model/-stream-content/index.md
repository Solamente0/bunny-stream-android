//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.model](../index.md)/[StreamContent](index.md)

# StreamContent

[androidJvm]\
class [StreamContent](index.md)(inputStream: [InputStream](https://developer.android.com/reference/kotlin/java/io/InputStream.html)) : OutgoingContent.ReadChannelContent

## Constructors

| | |
|---|---|
| [StreamContent](-stream-content.md) | [androidJvm]<br>constructor(inputStream: [InputStream](https://developer.android.com/reference/kotlin/java/io/InputStream.html)) |

## Properties

| Name | Summary |
|---|---|
| [contentLength](content-length.md) | [androidJvm]<br>open override val [contentLength](content-length.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [contentType](content-type.md) | [androidJvm]<br>open override val [contentType](content-type.md): ContentType |
| [headers](index.md#-1211925669%2FProperties%2F-1951609706) | [androidJvm]<br>open val [headers](index.md#-1211925669%2FProperties%2F-1951609706): Headers |
| [status](index.md#-1345698009%2FProperties%2F-1951609706) | [androidJvm]<br>open val [status](index.md#-1345698009%2FProperties%2F-1951609706): HttpStatusCode? |

## Functions

| Name | Summary |
|---|---|
| [getProperty](index.md#543801704%2FFunctions%2F-1951609706) | [androidJvm]<br>open fun &lt;[T](index.md#543801704%2FFunctions%2F-1951609706) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [getProperty](index.md#543801704%2FFunctions%2F-1951609706)(key: AttributeKey&lt;[T](index.md#543801704%2FFunctions%2F-1951609706)&gt;): [T](index.md#543801704%2FFunctions%2F-1951609706)? |
| [readFrom](index.md#-1500131473%2FFunctions%2F-1951609706) | [androidJvm]<br>open fun [readFrom](index.md#-1500131473%2FFunctions%2F-1951609706)(range: [LongRange](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-long-range/index.html)): ByteReadChannel<br>open override fun [readFrom](read-from.md)(): ByteReadChannel |
| [setProperty](index.md#-975814741%2FFunctions%2F-1951609706) | [androidJvm]<br>open fun &lt;[T](index.md#-975814741%2FFunctions%2F-1951609706) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [setProperty](index.md#-975814741%2FFunctions%2F-1951609706)(key: AttributeKey&lt;[T](index.md#-975814741%2FFunctions%2F-1951609706)&gt;, value: [T](index.md#-975814741%2FFunctions%2F-1951609706)?) |
| [trailers](index.md#523718201%2FFunctions%2F-1951609706) | [androidJvm]<br>open fun [trailers](index.md#523718201%2FFunctions%2F-1951609706)(): Headers? |
