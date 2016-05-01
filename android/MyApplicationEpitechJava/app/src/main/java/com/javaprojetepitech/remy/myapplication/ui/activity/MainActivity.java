package com.javaprojetepitech.remy.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.javaprojetepitech.remy.myapplication.R;
import com.javaprojetepitech.remy.myapplication.adapter.ContentAdapter;
import com.javaprojetepitech.remy.myapplication.model.Content;
import com.javaprojetepitech.remy.myapplication.webservice.Service;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://212.129.30.69:4242/";

    private RecyclerView recyclerView;
    private ContentAdapter mAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ON CLICK CIRCLE BUTTON
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                startActivity(intent);
            }
        });

        // CREATE HTTP CLIENT
        RestAdapter adapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        // USE MY SERVICE CLASS WITH ALL API CALLS (IMPORTANT FIL)
        Service api = adapter.create(Service.class);

        // GET OCAML JSON WITH RETROFIT LIB
        api.ocaml(new Callback<ArrayList<ArrayList<Content>>>() {
            @Override
            public void success(ArrayList<ArrayList<Content>> arrayContentLists, Response response) {
                if (arrayContentLists.size() > 0) {
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

                    // SEND DATA TO ADAPTER
                    mAdapter = new ContentAdapter(arrayContentLists.get(0));

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }
}