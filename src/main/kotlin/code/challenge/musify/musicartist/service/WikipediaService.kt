package code.challenge.musify.musicartist.service

import code.challenge.musify.musicartist.client.WikipediaClient
import org.springframework.stereotype.Service

@Service
class WikipediaService(private val wikipediaClient: WikipediaClient) {
    suspend fun getArtistSummary(title: String) = wikipediaClient.getPageSummaryForTitle(title)
}
