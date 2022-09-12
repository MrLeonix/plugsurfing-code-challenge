package code.challenge.musify.albumcovers.service

import code.challenge.musify.albumcovers.client.CoverArtArchiveClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class CoverArtArchiveService(private val coverArtArchiveClient: CoverArtArchiveClient) : AlbumCoversService {
    private suspend fun getReleaseGroup(id: UUID) = coverArtArchiveClient.findAlbumCoverImages(id)

    override suspend fun getAlbumCovers(id: UUID) = getReleaseGroup(id)
}
