package code.challenge.musify.musicartist.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import javax.validation.constraints.NotBlank

@ConstructorBinding
@ConfigurationProperties(prefix = "wikidata")
data class WikiDataConfigProperties(@field:NotBlank val apiUrl: String)
