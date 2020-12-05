package com.reactive.practice.configurations;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

//@EnableReactiveMongoRepositories
public class MongoDbReactiveConfig extends AbstractReactiveMongoConfiguration {

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://localhost:27017/reactive-services");
    }

    @Override
    protected String getDatabaseName() {
        return "reactive-services";
    }

}
