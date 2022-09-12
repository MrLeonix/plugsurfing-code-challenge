package code.challenge.musify.musicartist.api

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.service.MusicArtistService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("music-artist")
class ArtistController(
    private val musicArtistService: MusicArtistService
) {
    @GetMapping("details/{mbid}")
    suspend fun getArtist(@PathVariable mbid: MBID) = musicArtistService.getArtist(mbid).toArtistResponse()
}
