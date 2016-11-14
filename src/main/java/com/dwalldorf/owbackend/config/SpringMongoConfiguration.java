package com.dwalldorf.owbackend.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class SpringMongoConfiguration extends AbstractMongoConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(SpringMongoConfiguration.class);

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.db}")
    private String db;

    @Value("${mongo.port}")
    private int port;

    @Override
    protected String getDatabaseName() {
        return db;
    }

    @Bean
    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(host, port);
    }

    @Bean
    public MongoClient mongoClient() throws Exception {
        return (MongoClient) mongo();
    }

    @Bean
    public Datastore datastore() throws Exception {
        Morphia morphia = new Morphia();

        Datastore datastore = morphia.createDatastore(mongoClient(), db);
        datastore.ensureIndexes();

        logger.info("created mongo indexes");

        return datastore;
    }
}
