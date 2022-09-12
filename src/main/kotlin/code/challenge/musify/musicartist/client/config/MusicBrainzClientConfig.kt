package code.challenge.musify.musicartist.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MusicBrainzClientConfig(val musicBrainzConfigProperties: MusicBrainzConfigProperties) {
    @Bean("musicBrainzWebClient")
    fun musicBrainzWebClient() = WebClient.builder()
        .baseUrl(musicBrainzConfigProperties.host)
        .build()
}
