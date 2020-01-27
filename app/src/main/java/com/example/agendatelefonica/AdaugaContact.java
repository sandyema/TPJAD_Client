package com.example.agendatelefonica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class AdaugaContact extends  AppCompatActivity {

    Button addButton;
    EditText nume;
    EditText email;
    EditText telefon1;
    EditText telefon2;
    EditText zinastere;


    private ProgressBar progressBar;

    private String token;
    private ArrayAdapter<Contact> adapter;

    ContactService contactService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_contact);

        token = getIntent().getStringExtra("token");

        contactService = ServiceFactory.getContactService();

        addButton = (Button) findViewById(R.id.addButton);

        nume = (EditText) findViewById(R.id.nume);
        email = (EditText) findViewById(R.id.email);
        telefon1 = (EditText) findViewById(R.id.telefon1);
        telefon2 = (EditText) findViewById(R.id.telefon2);
        zinastere = (EditText) findViewById(R.id.zinastere);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String,String> contact;
                contact = handleAdd();
                contactService.addContact(contact).enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, retrofit2.Response<MyResponse> response) {
                        if(response.body().getResponse().equals("true")) {
                            startActivity(new Intent(AdaugaContact.this, MainActivity.class));
                            SharedPreferences sharedpreferences = getSharedPreferences("myprefs",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("items", contact.toString());
                            editor.commit();
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AdaugaContact.this);
                            builder.setTitle("Alert");
                            builder.setMessage("Something went wrong.");
                            builder.setNegativeButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                            dialog.show();
                            System.out.println("Error at request for adding the car");

                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {

                    }
                });
            }
        });

    }

    private HashMap<String,String> handleAdd() {

        String numeContact = nume.getText().toString();
        String Nrtelefon1 = telefon1.getText().toString();
        String Nrtelefon2 = telefon2.getText().toString();
        String emailContact = email.getText().toString();
        String ziNastere = zinastere.getText().toString();

        String errors = "";



            progressBar.setVisibility(View.VISIBLE);


            RequestQueue queue = Volley.newRequestQueue(AdaugaContact.this);


            HashMap<String, String> contact = new HashMap<String, String>();

            contact.put("contactNume", numeContact);
            contact.put("contactEmail", emailContact);
            contact.put("numartelefon1", Nrtelefon1);
            contact.put("numartelefon2", Nrtelefon2);
            contact.put("ziNastere", ziNastere);

            return contact;

        }

        public void showAlert (String title, String content){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(content);
            alertDialog.show();
        }
    }

