package code.challenge.musify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@ConfigurationPropertiesScan
@EnableConfigurationProperties
@EnableMongoRepositories
@SpringBootApplication
class MusifyApplication

fun main(args: Array<String>) {
    runApplication<MusifyApplication>(*args)
}
