package code.challenge.musify.albumcovers.service

import code.challenge.musify.albumcovers.client.response.CoverArtArchiveReleaseGroupResponse
import java.util.*

interface AlbumCoversService {
    suspend fun getAlbumCovers(id: UUID): CoverArtArchiveReleaseGroupResponse?
}
