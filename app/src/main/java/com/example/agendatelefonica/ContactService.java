package com.example.agendatelefonica;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ContactService {


    @POST("addContact")
    @Headers("Content-Type: application/json")
    Call<MyResponse> addContact(@Body HashMap<String, String> contact);
}

