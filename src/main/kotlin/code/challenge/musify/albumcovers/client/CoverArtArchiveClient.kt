package code.challenge.musify.albumcovers.client

import code.challenge.musify.albumcovers.client.response.CoverArtArchiveReleaseGroupResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import java.util.*

@Component
class CoverArtArchiveClient(private val coverArtArchiveWebClient: WebClient) {
    suspend fun findAlbumCoverImages(id: UUID) =
        coverArtArchiveWebClient.get()
            .uri("/release-group/$id")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .awaitBodyOrNull<CoverArtArchiveReleaseGroupResponse>()
}
