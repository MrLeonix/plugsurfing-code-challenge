package code.challenge.musify.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableReactiveMongoRepositories
@Profile("Reactive")
class MongoDBConfig(@Value("#{spring.data.mongodb.database}") val database: String) :
    AbstractReactiveMongoConfiguration() {
    override fun getDatabaseName() = database
}
