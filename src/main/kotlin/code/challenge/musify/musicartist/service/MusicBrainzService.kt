package code.challenge.musify.musicartist.service

import code.challenge.musify.MBID
import code.challenge.musify.albumcovers.service.AlbumCoversService
import code.challenge.musify.musicartist.client.MusicBrainzClient
import code.challenge.musify.musicartist.client.response.WikiDataEntityDataResponse
import code.challenge.musify.musicartist.client.response.WikipediaPageSummaryResponse
import code.challenge.musify.musicartist.repository.MusicArtistRepository
import code.challenge.musify.musicartist.repository.model.MusicArtist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class MusicBrainzService(
    private val albumCoversService: AlbumCoversService,
    private val musicArtistRepository: MusicArtistRepository,
    private val musicBrainzClient: MusicBrainzClient,
    private val wikiDataService: WikiDataService,
    private val wikipediaService: WikipediaService
) : MusicArtistService {
    private suspend fun getMusicBrainzArtist(id: MBID) = musicBrainzClient.findArtistById(id)

    override suspend fun getArtist(id: MBID): MusicArtist = run {
        var musicArtist = withContext(Dispatchers.IO) {
            musicArtistRepository.findByMbid(id)
        }
        if (musicArtist != null) return musicArtist

        val musicArtistResponse = getMusicBrainzArtist(id)
        val albums = musicArtistResponse.releaseGroups.map { releaseGroup ->
            val imageUrl =
                albumCoversService.getAlbumCovers(releaseGroup.id)?.images?.firstOrNull { it.front }?.imageUrl
            releaseGroup.toMusicArtistAlbum(imageUrl)
        }

        val wikiDataEntityId = musicArtistResponse.getWikiDataEntityId()
        val wikidata: WikiDataEntityDataResponse?
        var wikipedia: WikipediaPageSummaryResponse? = null
        if (wikiDataEntityId != null) {
            wikidata = wikiDataService.getEntityDataById(wikiDataEntityId)
            wikipedia =
                wikipediaService.getArtistSummary(
                    wikidata.getWikipediaTitle(wikiDataEntityId) ?: musicArtistResponse.name
                )
        }

        musicArtist =
            MusicArtist(
                albums,
                musicArtistResponse.country,
                wikipedia?.extractHtml,
                musicArtistResponse.disambiguation,
                musicArtistResponse.gender,
                UUID.randomUUID(),
                musicArtistResponse.name,
                musicArtistResponse.id,
                wikiDataEntityId
            )
        return musicArtistRepository.save(musicArtist)
    }
}
