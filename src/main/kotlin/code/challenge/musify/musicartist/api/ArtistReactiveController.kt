package code.challenge.musify.musicartist.api

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.service.MusicArtistReactiveService
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("music-artist")
@Profile("Reactive")
class ArtistReactiveController(
    val musicArtistService: MusicArtistReactiveService
) {
    @GetMapping("details/{mbid}")
    fun getArtist(@PathVariable mbid: MBID) = musicArtistService.getArtist(mbid)
}
