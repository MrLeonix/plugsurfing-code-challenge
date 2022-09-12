package code.challenge.musify.musicartist.repository.model

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.api.response.MusicArtistResponse
import code.challenge.musify.shared.enums.Gender
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class MusicArtist(
    val albums: List<Album>? = mutableListOf(),
    val country: String,
    val description: String?,
    val disambiguation: String,
    val gender: Gender,
    val id: UUID,
    val name: String,
    val mbid: MBID,
    val wikiDataEntityId: String?,
) {
    fun toArtistResponse() = MusicArtistResponse(
        mbid,
        name,
        gender,
        country,
        disambiguation,
        description,
        albums
    )

    data class Album(
        val id: UUID,
        val title: String,
        var imageUrl: String? = null
    )
}
