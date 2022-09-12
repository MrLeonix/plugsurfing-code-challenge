package code.challenge.musify.musicartist.client.response

import code.challenge.musify.MBID
import code.challenge.musify.musicartist.repository.model.MusicArtist
import code.challenge.musify.shared.enums.Gender
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class MusicBrainzArtistResponse(
    val id: MBID,
    val name: String,
    val gender: Gender,
    val country: String,
    val disambiguation: String,
    @JsonProperty("release-groups")
    val releaseGroups: List<ReleaseGroup>,
    val relations: List<Relation>,
) {
    fun getWikiDataEntityId() = relations.first { it.type == "wikidata" }.url.resource.substringAfterLast("/")

    data class Relation(
        val type: String,
        val url: RelationURL
    ) {
        data class RelationURL(val resource: String)
    }

    data class ReleaseGroup(
        val id: UUID,
        val title: String
    ) {
        fun toMusicArtistAlbum(imageUrl: String? = null) = MusicArtist.Album(id, title, imageUrl)
    }
}
