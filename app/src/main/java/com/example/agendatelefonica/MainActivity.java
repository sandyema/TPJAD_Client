package com.example.agendatelefonica;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DataObject> dataObjectList = new ArrayList<>();

    private static String IDCONTACT;

    private static final int RESULT_KEY = 13;
    private String idContact;
    LinearLayout linearLayout;
    EditText cautare;
    String nume;
    Button adaugaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideSoftKeyboard();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cautare=(EditText)findViewById(R.id.cautare);
        adaugaButton=(Button)findViewById(R.id.adaugaButton);

        getContacte();

        ListView myListView = (ListView) findViewById(R.id.myListView);
        CustomAdapter customAdapter=new CustomAdapter();
        myListView.setAdapter(customAdapter);


        adaugaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdaugaContact.class);
                startActivity(intent);

            }
        });

        cautare.addTextChangedListener(new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                nume = String.valueOf(cautare.getText().toString().trim());

                if (cautare.getText().toString().trim().length() != 0) {

                    getContactesearch(nume);
                    customAdapter.notifyDataSetChanged();

                }
                else  {

                    getContacte();
                    customAdapter.notifyDataSetChanged();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setPoza(DataObject b,JSONObject object) throws JSONException {
        if (object.getString("contactNume").equals("Sandy"))
            b.setPoza(String.valueOf(R.drawable.sandy));
        else
            if(object.getString("contactNume").equals("Lore"))
            b.setPoza(String.valueOf(R.drawable.lore));
            else
                if(object.getString("contactNume").equals("Andreea"))
                    b.setPoza(String.valueOf(R.drawable.andreea));
                else
                     if(object.getString("contactNume").equals("Aby"))
                        b.setPoza(String.valueOf(R.drawable.aby));
                     else
                         b.setPoza(String.valueOf(R.drawable.avatar));

    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    private void getContacte() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://192.168.100.3:8080/AgendaTelefonica/getAll", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataObjectList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("contact");
                    System.out.println(jsonArray);
                    for (int i = 0; jsonArray.length() > i; i++) {
                        final JSONObject object = jsonArray.getJSONObject(i);
//                        System.out.println("_________RESPONSE__________"+object);

                        DataObject b = new DataObject();


                        b.setContactId(Integer.valueOf(object.getString("contactId")));
                        b.setContactNume(object.getString("contactNume"));
                        setPoza(b,object);

                        dataObjectList.add(b);

                    }

                } catch (JSONException e) {
                    System.out.println("________******EROARE******__________"+e);

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("________EROARE__________"+error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
    private void getContactesearch(String numeSearch) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://192.168.100.3:8080/AgendaTelefonica/searchContacte/"+numeSearch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataObjectList.clear();
                System.out.println("_________DA___________"+dataObjectList);
                System.out.println("RESPONSE*******"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("DataObject");
                    for (int i = 0; jsonArray.length() > i; i++) {
                        final JSONObject object = jsonArray.getJSONObject(i);
                        DataObject b = new DataObject();

                        b.setContactNume(object.getString("contactNume"));
                        setPoza(b, object);
                        dataObjectList.add(b);

                    }

                    System.out.println("__________DO FINAL_________"+dataObjectList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        System.out.println("SIZEEE________"+dataObjectList.size());
    }
    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return dataObjectList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            //LinearLayout layout=(LinearLayout) view.findViewById(R.id.layout);

            hideSoftKeyboard();

            view = getLayoutInflater().inflate(R.layout.model, null);
            TextView nume = (TextView) view.findViewById(R.id.nume);
            ImageView poza= (ImageView) view.findViewById(R.id.poza);



            nume.setText(dataObjectList.get(position).getContactNume());
           // System.out.println("__________NUME______"+nume.getText());
            poza.setImageResource(Integer.parseInt(dataObjectList.get(position).getPoza()));


            nume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DetaliiContact.class);
                    idContact= String.valueOf(dataObjectList.get(position).getContactId());
                    intent.putExtra(IDCONTACT, idContact);
                    startActivityForResult(intent, RESULT_KEY);

                }
            });

            return view;
        }

    }
}
