package com.example.hduser.karmaalab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Bundle bundleLogin = new Bundle();

    SharedPreferences mPreferences;

    public static final String PREFS_NAME = "PTPrefsFile";
    public static final String SessionT = "Token";
    public static final String userId = "userIdKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

       /* mPreferences = MainActivity.this.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);*/
        mPreferences = getSharedPreferences(SessionT, Context.MODE_PRIVATE);


        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private void userLogin() {

      /*  final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter the password");
            editTextPassword.requestFocus();
            return;
        }*/

                        /*if (editTextUsername.getText().toString().equals("user") &
                             editTextPassword.getText().toString().equals("pass")){

                          Toast.makeText(MainActivity.this,"Username and password is correct",
                                    Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(getApplicationContext(), AdminHomeActivity.class));
                          finish();

                        }else if(editTextUsername.getText().toString().equals("user1") &
                                editTextPassword.getText().toString().equals("pass1")){

                            Toast.makeText(MainActivity.this,"Username and password is correct",
                            Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Username and password is not correct",
                                    Toast.LENGTH_SHORT).show();
                            *//*attempt_counter--;
                            attempt.setText(Integer.toString(attempt_counter));
                            if(attempt_counter==0)
                                login_button.setEnabled(false);*//*
                        }*/

            /*final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");*/
//        pDialog.show();
            RequestQueue queue = Volley.newRequestQueue(this);
          /*  JSONObject obj1 = new JSONObject();
            JSONArray obj3=new JSONArray();*/
            JSONObject obj2=new JSONObject();
            try {

               /* obj1.put("client_type",Constants.CLIENT_TYPE);
                obj1.put("ms_service_type","authentication");
                obj1.put("ms_service","login");*/

                obj2.put("username", editTextUsername.getText().toString());
                obj2.put("password", editTextPassword.getText().toString());
                /*obj3.put(obj2);
                obj1.put("ms_data",obj3);*/

            } catch (Exception e) {

            }

            System.out.println("req:"+obj2);
            Log.d("TAGreq", ""+obj2);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "https://varqr.karmaalab.net:8000/api-token-auth/", obj2
                    , new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {


                    JSONObject jsonobject = null;
                    try {


                        /*String msg = response.getString("message");
                        String st=response.getString("status");*/
                       /* if((msg.equals("User authorised"))&&(st.equals("Success"))){*/

                            if(response.equals("token")){
                            jsonobject = response.getJSONObject("token");
                        //    JSONObject childJSONObject = jsonMainArr.getJSONObject(0);
                       /* bundleLogin.putString("user_name",childJSONObject.getString("user_name"));
                        bundleLogin.putString("user_id",childJSONObject.getString("user_id"));
                        bundleLogin.putString("session_token",childJSONObject.getString("session_token"));
*/                              SharedPreferences.Editor editor = mPreferences.edit();
                                editor.putString(SessionT, response.getString("token"));
                                editor.commit();

                                System.out.println("req:"+SessionT);

                            Intent in=new Intent(getApplicationContext(), HomeActivity.class);
                            in.putExtras(bundleLogin);
                            startActivity(in);
                            //pDialog.cancel();
                        /*}else if((msg.equals("User Authentication Failed"))&&(st.equals("Error"))) {
                            Toast.makeText(getApplicationContext(),
                                    "Please use valid username and password",
                                    Toast.LENGTH_LONG).show();
                        }*/}else {

                                /*SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString(SessionT, jsonobject.getString("token"));
                                editor.apply();*/

                              /* SharedPreferences.Editor editor = mPreferences.edit();
                                editor.putString(SessionT,jsonobject.getString("token"));
                                editor.apply();*/
                                SharedPreferences.Editor editor = mPreferences.edit();
                                editor.putString(SessionT, response.getString("token"));
                                editor.commit();

                                System.out.println("req:"+SessionT);


                                Intent in=new Intent(getApplicationContext(), AdminHomeActivity.class);
                                startActivity(in);
                                Toast.makeText(getApplicationContext(),"Logged in successfully",Toast.LENGTH_LONG).show();
                            }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("TAGres", response.toString());

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(),
                                "Time out " ,
                                Toast.LENGTH_LONG).show();

                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getApplicationContext(),
                                "Authentication failed " ,
                                Toast.LENGTH_LONG).show();

                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

            };
            queue.add(jsonObjReq);
        }
    }