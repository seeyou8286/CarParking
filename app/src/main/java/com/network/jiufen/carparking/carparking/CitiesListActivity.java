package com.network.jiufen.carparking.carparking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.List;

public class CitiesListActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_list);
    }


    public MongoDatabase getMongoDBConnection()
    {
        MongoClientURI uri = new MongoClientURI(
                "mongodb://chachen:Happy%402013@cluster0-shard-00-00-hg4j1.mongodb.net:27017,cluster0-shard-00-01-hg4j1.mongodb.net:27017,cluster0-shard-00-02-hg4j1.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("car");
        return database;
    }

    public List<String> getCitiesList(MongoDatabase database)
    {
        MongoCollection<Document> collection =  database.getCollection("cities");
        Document document = collection.find().first();
        List<String> list = (List)document.get("name");
        return list;
    }


    @Override
    public void onClick(View view) {

    }
}
