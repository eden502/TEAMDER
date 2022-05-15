package MongoDBConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.concretepage.repository")
public class MongoDBConfig {
	public String getDatabaseName() {
		return "myFirstDatabase";
	}
	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate template = new MongoTemplate(MongoClients.create(
				"mongodb+srv://kerenrachev:123123Kk@integrativit.djvrh.mongodb.net/"+getDatabaseName()+"?retryWrites=true&w=majority"),
				getDatabaseName());
		return template;
	}
} 