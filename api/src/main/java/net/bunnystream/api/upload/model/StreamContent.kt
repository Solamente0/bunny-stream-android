package net.bunnystream.api.upload.model

import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.util.cio.toByteReadChannel
import io.ktor.utils.io.ByteReadChannel
import java.io.InputStream

class StreamContent(private val inputStream: InputStream) : OutgoingContent.ReadChannelContent() {
    override fun readFrom(): ByteReadChannel {
        return inputStream.toByteReadChannel()
    }
    override val contentType = ContentType.Application.OctetStream
    override val contentLength: Long = inputStream.available().toLong()
}