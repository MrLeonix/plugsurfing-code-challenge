package code.challenge.musify.musicartist.client

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.client.response.MusicBrainzArtistResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.client.createExceptionAndAwait

@Component
class MusicBrainzClient(private val musicBrainzWebClient: WebClient) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun findArtistById(id: MBID) =
        musicBrainzWebClient.get().uri("/artist/$id?inc=url-rels+release-groups")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .awaitExchange { response ->
                if (response.statusCode().is2xxSuccessful) {
                    response.awaitBody<MusicBrainzArtistResponse>()
                } else {
                    logger.error("An error has occurred while fetching artist's information.")
                    throw response.createExceptionAndAwait()
                }
            }
}
