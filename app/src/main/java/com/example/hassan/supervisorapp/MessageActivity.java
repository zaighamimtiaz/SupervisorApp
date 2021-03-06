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

public class MessageActivity extends AppCompatActivity {

    EditText msg;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        msg = (EditText)findViewById(R.id.msg);
        sendBtn = (Button)findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message = msg.getText().toString();
                        if ( message.equals("") )
                            Toast.makeText(getApplicationContext(),"Please Write Something ....",Toast.LENGTH_SHORT).show();

                        else {

                            String url = "https://fyp-loc-detect.herokuapp.com/users/sendNotif";

                            JSONObject obj = new JSONObject();

                            try {
                                obj.put("message", message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                    Request.Method.POST, url,obj, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getApplicationContext(),"Message Sent ...!!!",Toast.LENGTH_SHORT).show();
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
