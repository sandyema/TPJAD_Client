package com.example.agendatelefonica;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    static String BASE_URL = "http://192.168.100.3:8080/AgendaTelefonica/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ContactService getContactService() { return retrofit.create(ContactService.class);}

}