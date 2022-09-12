package code.challenge.musify.musicartist.service

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.repository.model.MusicArtist
import org.springframework.context.annotation.Profile
import reactor.core.publisher.Mono

@Profile("Reactive")
interface MusicArtistReactiveService {
    fun getArtist(id: MBID): Mono<MusicArtist>
}
