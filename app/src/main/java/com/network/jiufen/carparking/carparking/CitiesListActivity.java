package com.network.jiufen.carparking.carparking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CitiesListActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_list);
        listView = (ListView) findViewById(R.id.listViewBasic);
        MongoClientURI uri = new MongoClientURI(
                "mongodb://chachen:Happy%402013@cluster0-shard-00-00-hg4j1.mongodb.net:27017,cluster0-shard-00-01-hg4j1.mongodb.net:27017,cluster0-shard-00-02-hg4j1.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("car");
        MongoCollection<Document> collection =  database.getCollection("cities");
        Document document = collection.find().first();
        System.out.println(document.toJson());
        List<String> list = (List)document.get("name");
//        List<String> list= new ArrayList<>();
//        list.add("111");
//        list.add("222");
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list));
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
