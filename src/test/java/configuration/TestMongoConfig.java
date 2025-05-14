package configuration;

import com.mongodb.client.MongoClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class TestMongoConfig {

//    @Bean
//    public MongodExecutable mongodExecutable() throws IOException {
//        MongodConfig mongodConfig = MongodConfig.builder()
//                .version(Version.Main.V4_0)
//                .net(new Net("localhost", 27018, Network.localhostIsIPv6()))
//                .build();
//        MongodStarter starter = MongodStarter.getDefaultInstance();
//        return starter.prepare(mongodConfig);
//    }
//
//    @Bean(destroyMethod = "close")
//    public MongoClient mongoClient(MongodExecutable mongodExecutable) throws IOException {
//        mongodExecutable.start(); // Запускаем встраиваемую MongoDB
//        return MongoClients.create("mongodb://localhost:27018");
//    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "test");
    }
}