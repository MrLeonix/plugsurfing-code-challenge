package code.challenge.musify.musicartist.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WikipediaClientConfig {
    @Bean("wikipediaWebClient")
    fun wikipediaWebClient(wikipediaConfigProperties: WikipediaConfigProperties) = WebClient.builder()
        .baseUrl(wikipediaConfigProperties.apiUrl)
        .build()
}
