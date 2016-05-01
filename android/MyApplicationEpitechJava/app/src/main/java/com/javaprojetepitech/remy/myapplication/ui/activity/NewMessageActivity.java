package com.javaprojetepitech.remy.myapplication.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.javaprojetepitech.remy.myapplication.R;
import com.javaprojetepitech.remy.myapplication.model.Remote;
import com.javaprojetepitech.remy.myapplication.webservice.Service;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewMessageActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://212.129.30.69:4242/";

    private EditText editTextPath;
    private EditText editTextRemote;
    private Button buttonSendPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // INIT VIEW OBJECT
        editTextPath = (EditText) findViewById(R.id.editTextPath);
        editTextRemote = (EditText) findViewById(R.id.editTextRemote);
        buttonSendPost = (Button) findViewById(R.id.buttonSendPost);

        // ON CLICK BUTTON
        buttonSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = editTextPath.getText().toString();
                String remote = editTextRemote.getText().toString();

                Remote tmpRemote = new Remote(remote);

                // CREATE HTTP CLIENT
                RestAdapter adapter = new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint(BASE_URL)
                        .build();

                Service api = adapter.create(Service.class);

                // TRY TO POST REMOTE
                api.postRemote(path, tmpRemote, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(NewMessageActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Toast.makeText(NewMessageActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}