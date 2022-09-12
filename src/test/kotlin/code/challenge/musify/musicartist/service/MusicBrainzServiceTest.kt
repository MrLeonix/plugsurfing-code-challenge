package code.challenge.musify.musicartist.service

import code.challenge.musify.MBID
import code.challenge.musify.albumcovers.client.response.CoverArtArchiveReleaseGroupResponse
import code.challenge.musify.albumcovers.service.AlbumCoversService
import code.challenge.musify.musicartist.client.MusicBrainzClient
import code.challenge.musify.musicartist.client.response.MusicBrainzArtistResponse
import code.challenge.musify.musicartist.client.response.WikiDataEntityDataResponse
import code.challenge.musify.musicartist.client.response.WikipediaPageSummaryResponse
import code.challenge.musify.musicartist.repository.MusicArtistRepository
import code.challenge.musify.musicartist.repository.model.MusicArtist
import code.challenge.musify.shared.enums.Gender
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class MusicBrainzServiceTest {
    @MockK
    private lateinit var albumCoversService: AlbumCoversService

    @MockK
    private lateinit var musicArtistRepository: MusicArtistRepository

    @MockK
    private lateinit var musicBrainzClient: MusicBrainzClient

    @MockK
    private lateinit var wikiDataService: WikiDataService

    @MockK
    private lateinit var wikipediaService: WikipediaService

    @InjectMockKs
    private lateinit var musicBrainzService: MusicBrainzService

    @Test
    fun `should try to get a record from database before fetching from MusicBrainz`() {
        // given
        val mbid = UUID.randomUUID()
        every {
            musicArtistRepository.findByMbid(mbid)
        } returns getMusicArtistMock(mbid)

        // when
        runBlocking { musicBrainzService.getArtist(mbid) }

        // then
        verify {
            musicArtistRepository.findByMbid(mbid)
        }
        coVerifyOrder(inverse = true) {
            musicBrainzClient.findArtistById(any())
            albumCoversService.getAlbumCovers(any())
            wikiDataService.getEntityDataById(any())
            wikipediaService.getArtistSummary(any())
        }
        verify(inverse = true) { musicArtistRepository.save(any()) }
    }

    @Test
    fun `should fetch data from MusicBrainz when there is no saved database and save found result`() {
        // given
        val mbid = UUID.randomUUID()
        val expectedMusicArtist = getMusicArtistMock(mbid)
        val musicBrainzArtistResponseMock = getMusicBrainzArtistResponseMock(
            mbid,
            expectedMusicArtist
        )
        val coverArtArchiveReleaseGroupResponseMock = getCoverArtArchiveReleaseGroupResponseMock()
        val wikiDataEntityDataResponseMock = getWikiDataEntityDataResponseMock(expectedMusicArtist)
        val wikipediaPageSummaryResponseMock = getWikipediaPageSummaryResponseMock(expectedMusicArtist)

        every {
            musicArtistRepository.findByMbid(mbid)
        } returns null
        coEvery { musicBrainzClient.findArtistById(mbid) } returns musicBrainzArtistResponseMock
        coEvery { albumCoversService.getAlbumCovers(any()) } returns coverArtArchiveReleaseGroupResponseMock
        coEvery {
            wikiDataService.getEntityDataById(expectedMusicArtist.wikiDataEntityId!!)
        } returns wikiDataEntityDataResponseMock
        coEvery {
            wikipediaService.getArtistSummary(
                wikiDataEntityDataResponseMock.getWikipediaTitle(expectedMusicArtist.wikiDataEntityId!!)
                    ?: expectedMusicArtist.name
            )
        } returns wikipediaPageSummaryResponseMock
        every { musicArtistRepository.save(any()) } returns mockk()

        // when
        val musicArtist = runBlocking { musicBrainzService.getArtist(mbid) }

        // then
        verify {
            musicArtistRepository.findByMbid(mbid)
        }
        coVerifyOrder {
            musicBrainzClient.findArtistById(mbid)
            albumCoversService.getAlbumCovers(any())
            wikiDataService.getEntityDataById(expectedMusicArtist.wikiDataEntityId!!)
            wikipediaService.getArtistSummary(expectedMusicArtist.name)
        }
        verify {
            musicArtistRepository.save(any())
        }
    }

    private fun getCoverArtArchiveReleaseGroupResponseMock() = CoverArtArchiveReleaseGroupResponse(
        listOf(
            CoverArtArchiveReleaseGroupResponse.Image(
                "https://cdn2.albumoftheyear.org/750x/album/208036-ouro.jpg",
                true
            )
        )
    )

    private fun getWikipediaPageSummaryResponseMock(expectedMusicArtist: MusicArtist) =
        WikipediaPageSummaryResponse(expectedMusicArtist.description!!)

    private fun getWikiDataEntityDataResponseMock(expectedMusicArtist: MusicArtist) =
        WikiDataEntityDataResponse(
            mapOf(
                expectedMusicArtist.wikiDataEntityId!! to WikiDataEntityDataResponse.EntityData(
                    mapOf(
                        "enwiki" to WikiDataEntityDataResponse.EntityData.SiteLink("Vitão")
                    )
                )
            )
        )

    private fun getMusicArtistMock(mbid: MBID = UUID.randomUUID()) = MusicArtist(
        mutableListOf(getMusicArtistAlbumMock()),
        "BR",
        "Pop singer",
        "<p>Starting his career very young, made huge success among teens</p>",
        Gender.MALE,
        UUID.randomUUID(),
        "Vitão",
        mbid,
        "Q60841629"
    )

    private fun getMusicArtistAlbumMock() = MusicArtist.Album(
        UUID.randomUUID(),
        "Ouro",
        "https://cdn2.albumoftheyear.org/750x/album/208036-ouro.jpg"
    )

    private fun getMusicBrainzArtistResponseMock(
        mbid: UUID,
        expectedMusicArtist: MusicArtist
    ) = MusicBrainzArtistResponse(
        mbid,
        expectedMusicArtist.name,
        expectedMusicArtist.gender,
        expectedMusicArtist.country,
        expectedMusicArtist.disambiguation,
        mutableListOf(getReleaseGroupMock()),
        mutableListOf(
            MusicBrainzArtistResponse.Relation(
                "wikidata",
                MusicBrainzArtistResponse.Relation.RelationURL("/${expectedMusicArtist.wikiDataEntityId}")
            )
        )
    )

    private fun getReleaseGroupMock() = MusicBrainzArtistResponse.ReleaseGroup(UUID.randomUUID(), "Ouro")
}
