package net.bunnystream.stream.domain

import arrow.core.Either

interface StreamRepository {
    suspend fun prepareStreaming(libraryId: Long): Either<String, String>
}