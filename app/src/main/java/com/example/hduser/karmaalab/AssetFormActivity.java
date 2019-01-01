package com.example.hduser.karmaalab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssetFormActivity extends AppCompatActivity {

    SharedPreferences mPreferences;
    private static final String TAG = AssetFormActivity.class.getSimpleName();

    private static final String PREFS_NAME ="PTPrefsFile" ;
    public static final String SessionT = "Token";
    public static final String userId = "userIdKey";
    String  sesTk;
    String message;

    TextView manufacturer, partname, partid, assetinfo, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_form);

        Bundle bundle = getIntent().getExtras();
        //message = bundle.getString("message");
        message= bundle.getString("id");


        mPreferences = getSharedPreferences(SessionT,
                Context.MODE_PRIVATE);
        if (mPreferences.contains(SessionT)) {
            sesTk=(mPreferences.getString(SessionT, "null"));
        }
       /* if (mPreferences.contains(userId)) {
            userid=(mPreferences.getString(userId, "null"));
        }*/

        ArrayList<Asset> itemList = new ArrayList<>();
        fillDummyData1(itemList);
    }


    private void fillDummyData1(final ArrayList<Asset> assetList) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
//        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject obj1 = new JSONObject();

        System.out.println("req:"+obj1);
        Log.d("TAGreq", ""+obj1);
        Log.d("TAGre", ""+message);
        String uri1 = String.format("https://varqr.karmaalab.net:8000/assets/all-detail-list/?id=%1$s", message);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAGres   ", response);

                        try {

                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonMainArr = obj.getJSONArray("spareparts");

                            JSONObject childJSONObject = jsonMainArr.getJSONObject(0);
                            manufacturer = (TextView) findViewById(R.id.eventN);
                            manufacturer.setText(childJSONObject.getString("manufacturer"));

                            assetinfo = (TextView) findViewById(R.id.event_em);
                            assetinfo.setText(childJSONObject.getString("AssetInfo"));

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
