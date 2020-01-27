package com.example.agendatelefonica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetaliiContact extends AppCompatActivity {

    final String server_url = "http://192.168.100.3:8080/AgendaTelefonica/detaliicontact";
    final String server_url_delete = "http://192.168.100.3:8080/AgendaTelefonica/deleteContact";

    private ArrayList<Contact> contactlist = new ArrayList<>();

    private static String IDCONTACT;
    String idcontact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaliicontact);

        ImageView Poza=(ImageView) findViewById(R.id.poza) ;
        TextView NumeContact=(TextView)findViewById(R.id.numecontact);
        TextView Telefon1=(TextView)findViewById(R.id.telefon1);
        TextView Telefon2=(TextView)findViewById(R.id.telefon2);
        TextView Email=(TextView)findViewById(R.id.email);
        TextView Zinastere=(TextView)findViewById(R.id.zinastere);
        Button deleteButton=(Button)findViewById(R.id.button);
        getDetalii(Poza,NumeContact,Telefon1,Telefon2,Email,Zinastere);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idcontact = bundle.getString(IDCONTACT);

        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelete();
            }
        });
        if(idcontact.equals(String.valueOf(1)))
            Poza.setImageResource(R.drawable.sandy);

    }
    public void getDetalii(ImageView poza, final TextView nume, final TextView telefon1, final TextView telefon2, final TextView email, final TextView zinastere){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idcontact = bundle.getString(IDCONTACT);

        }

        if(idcontact.equals(String.valueOf(1)))
            poza.setImageResource(R.drawable.sandy);
        else
         if(idcontact.equals(String.valueOf(2)))
            poza.setImageResource(R.drawable.lore);
         else
            if(idcontact.equals(String.valueOf(4)))
                poza.setImageResource(R.drawable.andreea);
            else
                if(idcontact.equals(String.valueOf(5)))
                    poza.setImageResource(R.drawable.aby);
                else
                    poza.setImageResource(R.drawable.avatar);

        RequestQueue queue = Volley.newRequestQueue(DetaliiContact.this);

        final JsonObjectRequest request_json = new JsonObjectRequest(
                Request.Method.GET,
                server_url+"/"+idcontact,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response != null) {
                                contactlist.clear();
                                Contact c = new Contact();
                                c.setContactId(Integer.valueOf(response.getString("contactId")));
                                c.setContactNume(String.valueOf(response.getString("contactNume")));
                                c.setNumartelefon1(String.valueOf(response.getString("numartelefon1")));
                                c.setNumartelefon2(String.valueOf(response.getString("numartelefon2")));
                                c.setContactEmail(String.valueOf(response.getString("contactEmail")));
                                c.setZiNastere(String.valueOf(response.getString("ziNastere")));

                                if(response.getString("numartelefon1").equals("null"))
                                    telefon1.setText("");
                                else
                                    telefon1.setText(String.valueOf(response.getString("numartelefon1")));

                                nume.setText(String.valueOf(response.getString("contactNume")));

                                if(response.getString("numartelefon2").equals("null"))
                                    telefon2.setText("");
                                else
                                    telefon2.setText(String.valueOf(response.getString("numartelefon2")));

                                if(response.getString("contactEmail").equals("null"))
                                    email.setText("");
                                else
                                email.setText(String.valueOf(response.getString("contactEmail")));

                                if(response.getString("ziNastere").equals("null"))
                                    zinastere.setText("");
                                else
                                zinastere.setText(String.valueOf(response.getString("ziNastere")));
                            } else {
                                System.out.println("========= Eroare la incarcarea atributelor ============");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
        queue.add(request_json);
    }
    public void handleDelete() {

            final Integer  id = Integer.valueOf(idcontact);
            RequestQueue queue = Volley.newRequestQueue(DetaliiContact.this);
            HashMap<String, String> contact = new HashMap<String, String>();
            contact.put("contactId", String.valueOf(id));
            JsonObjectRequest request_json = new JsonObjectRequest(
                    server_url_delete,
                    new JSONObject(contact),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("response").equals("true")) {
                                    Intent intent = new Intent(DetaliiContact.this, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    System.out.println("Error at request for deleting the contact");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    });
            queue.add(request_json);

        }


}
