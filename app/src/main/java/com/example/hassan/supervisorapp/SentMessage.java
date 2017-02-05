package com.example.hassan.supervisorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SentMessage extends AppCompatActivity {

    Bundle data;
    String message;
    String dum = "Message sent to selected users...!!!";
    String url = "http://192.168.1.104:3000/users/sendNotifMultiple";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_message);

         data = getIntent().getExtras();
        int[] array = data.getIntArray("uid");
        message = data.getString("message");

        TextView tv = (TextView)findViewById(R.id.tv);
        tv.setText(dum);

        JSONObject obj = new JSONObject();

        try {
            obj.put("message", message);
            obj.put("uid",Arrays.toString(array));
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
