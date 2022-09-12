package code.challenge.musify.albumcovers.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import javax.validation.constraints.NotBlank

@ConstructorBinding
@ConfigurationProperties(prefix = "coverartarchive")
data class CoverArtArchiveConfigProperties(@field:NotBlank val apiUrl: String)
