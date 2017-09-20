package com.network.jiufen.carparking.carparking;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.*;

/**
 * Created by asus on 2017/9/20.
 */
public class MongoDbConnectionTest {

    @Test
    public void getConnectionTest() {

        MongoClientURI uri = new MongoClientURI(
                "mongodb://chachen:Happy%402013@cluster0-shard-00-00-hg4j1.mongodb.net:27017,cluster0-shard-00-01-hg4j1.mongodb.net:27017,cluster0-shard-00-02-hg4j1.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("car");
        MongoCollection<Document> collection =  database.getCollection("cities");
        Document document = collection.find().first();
        System.out.println(document.toJson());
        List<String> list = (List)document.get("name");
        System.out.println(list.get(1));
    }


    @Test
    public void getConnectionTest2() {

        MongoClientURI uri = new MongoClientURI(
                "mongodb://chachen:Happy%402013@cluster0-shard-00-00-hg4j1.mongodb.net:27017,cluster0-shard-00-01-hg4j1.mongodb.net:27017,cluster0-shard-00-02-hg4j1.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("car");
        MongoCollection<Document> collection =  database.getCollection("cities");
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

}