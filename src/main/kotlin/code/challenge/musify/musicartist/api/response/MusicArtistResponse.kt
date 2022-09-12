package code.challenge.musify.musicartist.api.response

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.repository.model.MusicArtist
import code.challenge.musify.shared.enums.Gender

data class MusicArtistResponse(
    val mbid: MBID,
    val name: String,
    val gender: Gender,
    val country: String,
    val disambiguation: String,
    val description: String?,
    val albums: List<MusicArtist.Album>? = mutableListOf(),
)
