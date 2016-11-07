package com.dwalldorf.owbackend.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class SpringMongoAutoConfiguration extends AbstractMongoConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(SpringMongoAutoConfiguration.class);

    private final static String DB_HOST = "127.0.0.1";
    private final static String DB_NAME = "owbackend";

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Bean
    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(DB_HOST);
    }

    @Bean
    public MongoClient mongoClient() throws Exception {
        return (MongoClient) mongo();
    }

    @Bean
    public Datastore datastore() throws Exception {
        Morphia morphia = new Morphia();

        Datastore datastore = morphia.createDatastore(mongoClient(), DB_NAME);
        datastore.ensureIndexes();

        logger.info("created mongo indexes");

        return datastore;
    }
}
