package code.challenge.musify.musicartist.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WikipediaClientConfig(val wikipediaConfigProperties: WikipediaConfigProperties) {
    @Bean("wikipediaWebClient")
    fun wikipediaWebClient() = WebClient.builder()
        .baseUrl(wikipediaConfigProperties.host)
        .build()
}
