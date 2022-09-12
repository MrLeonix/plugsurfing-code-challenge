package code.challenge.musify.albumcovers.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class CoverArtArchiveClientConfig(private val coverArtArchiveConfigProperties: CoverArtArchiveConfigProperties) {
    @Bean("coverArtArchiveWebClient")
    fun coverArtArchiveWebClient() = WebClient.builder()
        .baseUrl(coverArtArchiveConfigProperties.apiUrl)
        .clientConnector(ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
        .build()
}
