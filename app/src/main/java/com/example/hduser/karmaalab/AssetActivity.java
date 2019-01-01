package com.example.hduser.karmaalab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ItemAdapter itemAdapter;
    private static ArrayList<Asset> itemList;
    SharedPreferences mPreferences;

    private static final String PREFS_NAME ="PTPrefsFile" ;
    public static final String SessionT = "Token";
  //  public static final String userId = "userIdKey";

    private static final String TAG = AssetActivity.class.getSimpleName();

    TextView CompanyName, Manufacturer, Category, Model;

    String  sesTk;
   // String userid;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset);

        mPreferences = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        mPreferences = getSharedPreferences(SessionT,
                Context.MODE_PRIVATE);


        if (mPreferences.contains(SessionT)) {
            sesTk=(mPreferences.getString(SessionT, "null"));
        }
        /*if (mPreferences.contains(userId)) {
            userid=(mPreferences.getString(userId, "null"));
        }*/
       // mPreferences = getSharedPreferences(userId, Context.MODE_PRIVATE);


          itemList = new ArrayList<>();



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //itemAdapter = new ItemAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);

        //final Asset asset = new Asset();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Asset asset = itemList.get(position);
                Intent intent = new Intent(getApplicationContext(), AssetFormActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                bundle.putString("id", asset.getId());
                intent.putExtras(bundle);
                startActivity(intent);

               // Event event = eventList.get(position);
               // Intent intent = new Intent(getActivity(), Event_details_view.class);
               // Bundle bundle = new Bundle();
               // bundle.putString("eventId", event.getEid());
               // intent.putExtras(bundle);
               // startActivity(intent);

            }
            public void onLongClick(View view, int position) {

            }
        }));

        fillDummyData(itemList);
    }

    /*private void fillDummyData(ArrayList<Asset> assetList) {
        Asset asset1 = new Asset();
        asset1.setCompanyName("OIl Circuit Breaker");
        asset1.setManufacturer("MEI");
        assetList.add(asset1);

        Asset asset2 = new Asset();
        asset2.setCompanyName("OIl Circuit Breaker");
        asset2.setManufacturer("MEI");
        assetList.add(asset2);

        Asset asset3 = new Asset();
        asset3.setCompanyName("Circuit Breaker");
        asset3.setManufacturer("MEI");
        assetList.add(asset3);

        Asset asset4 = new Asset();
        asset4.setCompanyName("Breaker");
        asset4.setManufacturer("MEI");
        assetList.add(asset4);

        *//*Asset asset5 = new Asset();
        asset5.setCompanyName("OIl Circuit Breaker");
        asset5.setManufacturer("MEI");
        assetList.add(asset5);

        Asset asset6 = new Asset();
        asset6.setCompanyName("OIl Circuit Breaker");
        asset6.setManufacturer("MEI");
        assetList.add(asset6);*//*

    }*/

    private void fillDummyData(final ArrayList<Asset> assetList) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
//        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject obj1 = new JSONObject();

        System.out.println("req:"+obj1);
        Log.d("TAGreq", ""+obj1);
       StringRequest stringRequest = new StringRequest(Request.Method.GET,
               "https://varqr.karmaalab.net:8000/assets/asset-list/",
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       Log.d("TAGres   ", response);

                       try {

                           JSONArray obj = new JSONArray(response);

                           for(int i=0 ;i<obj.length();i++) {
                               JSONObject obj1 = obj.getJSONObject(i);
                               Asset asset1 = new Asset();
                               asset1.setCompanyName(obj1.getString("name"));
                               asset1.setManufacturer(obj1.getString("manufacturer"));
                               asset1.setCategory(obj1.getString("category"));
                               asset1.setModel(obj1.getString("model"));
                               asset1.setId(obj1.getString("id"));
                             //  Asset asset1 = new Asset(CompanyName, Manufacturer, Category, Model);
                               assetList.add(asset1);
                               itemAdapter = new ItemAdapter(assetList);
                               recyclerView.setAdapter(itemAdapter);




                               //String id = obj1.getString("id");
                              /* SharedPreferences.Editor editor = mPreferences.edit();
                               editor.putString(userId,asset1.getId());
                               editor.commit();*/
                           }
                          /* CompanyName = (TextView) findViewById(R.id.txtCompanyName);
                           CompanyName.setText(obj.getString("name"));*/
                       }  catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Log.d(TAG, "TAGerr "+error.getMessage());
                   }
               })

       {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
               // String base64EncodedCredentials = Base64.encodeToString(sesTk.getBytes(), Base64.NO_WRAP);
                //String ses1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6IiIsInVzZXJfaWQiOjUsInVzZXJuYW1lIjoiY2hpbGxlcnBsYW50dXNlciIsImV4cCI6MTU0ODgzMjU5NH0.ZQDACKRkaL9_r4gDYojyhsnxuUjedwOUvi1OFBJcnjs";
                headers.put("Content-Type", "application/json");
              //  headers.put("Authorization", "JWT" + " " + base64EncodedCredentials);
                headers.put("Authorization","JWT "+ sesTk);
                return headers;
            }

        };
        queue.add(stringRequest);
    }
    }

