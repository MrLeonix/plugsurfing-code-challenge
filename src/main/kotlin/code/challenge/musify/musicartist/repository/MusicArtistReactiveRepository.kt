package code.challenge.musify.musicartist.repository

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.repository.model.MusicArtist
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

@Profile("Reactive")
interface MusicArtistReactiveRepository : ReactiveMongoRepository<MusicArtist, String> {
    fun findByMbid(id: MBID): Mono<MusicArtist>
}
