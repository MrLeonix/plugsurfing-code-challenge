package code.challenge.musify.musicartist.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MusicBrainzClientConfig {
    @Bean("musicBrainzWebClient")
    fun musicBrainzWebClient(musicBrainzConfigProperties: MusicBrainzConfigProperties) = WebClient.builder()
        .baseUrl(musicBrainzConfigProperties.apiUrl)
        .build()
}
