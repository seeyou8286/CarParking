package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.network.jiufen.carparking.carparking.R;

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

        final List<String> list= new ArrayList<>();
        list.add("深圳宝安");
        list.add("广州白云");
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(list.get(position));
                Intent intent = new Intent(CitiesListActivity.this,BriefActivity.class);
                startActivity(intent);
            }
        });
    }



    public MongoDatabase getMongoDBConnection()
    {
        MongoClientURI uri = new MongoClientURI(
                "mongodb://localhost:27017");

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
