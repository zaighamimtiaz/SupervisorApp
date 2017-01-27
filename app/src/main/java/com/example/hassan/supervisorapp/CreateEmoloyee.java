package com.example.hassan.supervisorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateEmoloyee extends AppCompatActivity {

    EditText firstName , email , pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_emoloyee);

        Button create = (Button)findViewById(R.id.create);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        firstName = (EditText)findViewById(R.id.firstName);

        create.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name , emailid , password;
                        String url = "http://fyp-loc-detect.herokuapp.com/users";

                        name = firstName.getText().toString();
                        emailid = email.getText().toString();
                        password = pass.getText().toString();

                        if ( name.equals("") || emailid.equals("") || password.equals("") ){
                            Toast.makeText(getApplicationContext(),"Fill Credentials...!!!",Toast.LENGTH_SHORT).show();
                        }

                        else {

                            JSONObject obj = new JSONObject();

                            try {
                                obj.put("name", name);
                                obj.put("emailid", emailid);
                                obj.put("password",password);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                    Request.Method.POST, url,obj, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    if ( response.has("msg") )
                                        Toast.makeText(getApplicationContext(),"User with the same email exists...!!!",
                                                Toast.LENGTH_SHORT).show();
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"New user Created...!!!",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),
                                            "Something went Wrong ...!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            );
                            AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                        }
                    }
                }
        );
    }
}
