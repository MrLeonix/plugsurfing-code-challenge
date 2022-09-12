package code.challenge.musify.musicartist.client.response

import com.fasterxml.jackson.annotation.JsonProperty

data class WikipediaPageSummaryResponse(
    @JsonProperty("extract_html")
    val extractHtml: String
)
