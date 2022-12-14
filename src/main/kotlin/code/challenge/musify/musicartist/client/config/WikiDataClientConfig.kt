package code.challenge.musify.musicartist.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WikiDataClientConfig {
    @Bean("wikiDataWebClient")
    fun wikiDataWebClient(wikiDataConfigProperties: WikiDataConfigProperties): WebClient {
        val maxBufferSize = 16 * 1024 * 1024
        val strategies = ExchangeStrategies.builder()
            .codecs {
                it.defaultCodecs().maxInMemorySize(maxBufferSize)
            }.build()
        return WebClient.builder()
            .exchangeStrategies(strategies)
            .baseUrl(wikiDataConfigProperties.apiUrl)
            .build()
    }
}
