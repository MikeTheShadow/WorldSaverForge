package com.miketheshadow.worldsaver.storage;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DataStorage {

    public static MongoCollection<Document> DATABASE = init();

    private static MongoCollection<Document> init() {
        if(DATABASE != null) return DATABASE;
        MongoClient client = new MongoClient(new MongoClientURI("mongodb://172.18.0.1:27017"));
        MongoDatabase database = client.getDatabase("BlockSaver");
        return database.getCollection("blocks");
    }
}
