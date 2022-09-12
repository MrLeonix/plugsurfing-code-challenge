package code.challenge.musify.musicartist.client

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.client.response.MusicBrainzArtistResponse
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
@Profile("Reactive")
class MusicBrainzReactiveClient(val musicBrainzWebClient: WebClient) {
    fun findArtistById(id: MBID) =
        musicBrainzWebClient.get().uri("/artist/$id?inc=url-rels+release-groups")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .retrieve().bodyToMono(MusicBrainzArtistResponse::class.java)
}
