package code.challenge.musify.musicartist.client.response

data class WikiDataEntityDataResponse(
    val entities: Map<String, EntityData>
) {
    fun getWikipediaTitle(entityDataId: String) = entities[entityDataId]?.sitelinks?.get("enwiki")?.title

    data class EntityData(
        val sitelinks: Map<String, SiteLink>
    ) {
        data class SiteLink(
            val title: String
        )
    }
}
