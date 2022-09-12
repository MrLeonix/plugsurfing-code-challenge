package code.challenge.musify.musicartist.service

import code.challenge.musify.musicartist.client.WikiDataClient
import org.springframework.stereotype.Service

@Service
class WikiDataService(private val wikiDataClient: WikiDataClient) {
    suspend fun getEntityDataById(entityDataId: String) = wikiDataClient.findEntityDataById(entityDataId)
}
