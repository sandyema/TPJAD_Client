package com.example.agendatelefonica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private ArrayList<DataObject> dataObjectList = new ArrayList<>();

    private static String IDCONTACT;
    private static final int RESULT_KEY = 13;
    private String idContact;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getContacte();


        ListView myListView = (ListView) findViewById(R.id.myListView);
        CustomAdapter customAdapter=new CustomAdapter();

        myListView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
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
    private void getContacte() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://192.168.100.3:8080/AgendaTelefonica/getAll", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataObjectList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("contact");
                    for (int i = 0; jsonArray.length() > i; i++) {
                        final JSONObject object = jsonArray.getJSONObject(i);
                        System.out.println("_________RESPONSE__________"+object);

                        DataObject b = new DataObject();


                        b.setContactId(Integer.valueOf(object.getString("contactId")));
                        b.setContactNume(object.getString("contactNume"));
                        setPoza(b,object);

                        dataObjectList.add(b);
                        System.out.println("_____DATAOBJECT_______"+dataObjectList);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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

           // LinearLayout layout=(LinearLayout) view.findViewById(R.id.layout);
            //hideSoftKeyboard();
            view = getLayoutInflater().inflate(R.layout.model, null);
            TextView nume = (TextView) view.findViewById(R.id.nume);
            ImageView poza= (ImageView) view.findViewById(R.id.poza);


            nume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DetaliiContact.class);
                    idContact= String.valueOf(dataObjectList.get(position).getContactId());
                    intent.putExtra(IDCONTACT, idContact);
                    startActivityForResult(intent, RESULT_KEY);

                }
            });

            nume.setText(dataObjectList.get(position).getContactNume());
            System.out.println("__________NUME______"+nume.getText());
            poza.setImageResource(Integer.parseInt(dataObjectList.get(position).getPoza()));

            return view;
        }

    }
}
