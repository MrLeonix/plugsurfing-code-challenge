package code.challenge.musify.musicartist.repository

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.repository.model.MusicArtist
import org.springframework.data.mongodb.repository.MongoRepository

interface MusicArtistRepository : MongoRepository<MusicArtist, String> {
    fun findByMbid(id: MBID): MusicArtist?
}
