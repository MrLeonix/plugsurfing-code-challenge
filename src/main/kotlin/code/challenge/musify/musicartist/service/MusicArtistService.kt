package code.challenge.musify.musicartist.service

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.repository.model.MusicArtist

interface MusicArtistService {
    suspend fun getArtist(id: MBID): MusicArtist
}
