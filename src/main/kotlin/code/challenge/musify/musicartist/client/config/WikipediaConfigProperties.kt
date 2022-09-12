package code.challenge.musify.musicartist.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "wikipedia")
data class WikipediaConfigProperties(val host: String)
