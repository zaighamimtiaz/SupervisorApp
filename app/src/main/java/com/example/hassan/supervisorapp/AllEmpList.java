package com.example.hassan.supervisorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllEmpList extends AppCompatActivity {

    ListView l;
    String url = "http://192.168.1.104:3000/users";
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Integer> list1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_emp_list);

        l = (ListView)findViewById(R.id.allEmpList);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    JSONObject jsonObject;

                    int len = response.length();
                    for (int i=0;i<len;i++){
                        jsonObject = response.getJSONObject(i);
                        list.add(jsonObject.getString("name"));
                        list1.add(jsonObject.getInt("id"));
                    }

                    ArrayAdapter<String> adapter =new ArrayAdapter<>(
                            getApplicationContext(),android.R.layout.simple_list_item_1,list);

                    l.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error ...!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        AppSingleton.getInstance(AllEmpList.this).addToRequestQueue(jsonArrayRequest);
    }
}
