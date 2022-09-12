package code.challenge.musify.albumcovers.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "coverartarchive")
data class CoverArtArchiveConfigProperties(val host: String)
