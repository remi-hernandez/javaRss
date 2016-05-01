package com.javaprojetepitech.remy.myapplication.webservice;

import com.javaprojetepitech.remy.myapplication.model.Content;
import com.javaprojetepitech.remy.myapplication.model.Remote;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface Service {
    @GET("/ocaml")
    public void ocaml(Callback<ArrayList<ArrayList<Content>>> response);

    @POST("/{path}")
    public void postRemote(@Path("path") String path, @Body Remote remote, Callback<Response> responseCallback);
}