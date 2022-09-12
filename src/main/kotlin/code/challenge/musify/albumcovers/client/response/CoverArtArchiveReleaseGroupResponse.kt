package code.challenge.musify.albumcovers.client.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CoverArtArchiveReleaseGroupResponse(val images: List<Image>) {
    data class Image(
        @JsonProperty("image")
        val imageUrl: String,
        val front: Boolean,
    )
}
