package code.challenge.musify.musicartist.service

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.client.MusicBrainzReactiveClient
import code.challenge.musify.musicartist.repository.MusicArtistReactiveRepository
import code.challenge.musify.musicartist.repository.model.MusicArtist
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.util.*

@Service
@Profile("Reactive")
class MusicBrainzReactiveService(
    val musicArtistRepository: MusicArtistReactiveRepository,
    val musicBrainzClient: MusicBrainzReactiveClient
) : MusicArtistReactiveService {
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun getMusicBrainzArtist(id: MBID) = musicBrainzClient.findArtistById(id)

    override fun getArtist(id: MBID) =
        musicArtistRepository.findByMbid(id)
            .also { logger.info("Requesting music artist with MBID $id") }
            .switchIfEmpty {
                Mono.empty<Any>().zipWith(getMusicBrainzArtist(id)) { _, musicArtistResponse ->
                    logger.info("Couldn't find any persisted data, retrieving from MusicBrainz.")
                    MusicArtist(
                        musicArtistResponse.releaseGroups.map { it.toMusicArtistAlbum() },
                        musicArtistResponse.country,
                        "",
                        musicArtistResponse.disambiguation,
                        musicArtistResponse.gender,
                        UUID.randomUUID(),
                        musicArtistResponse.name,
                        musicArtistResponse.id,
                        ""
                    )
                }
            }

}
