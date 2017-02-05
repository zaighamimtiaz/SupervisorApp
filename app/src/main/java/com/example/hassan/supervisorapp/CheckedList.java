package com.example.hassan.supervisorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
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
import java.util.Arrays;

public class CheckedList extends AppCompatActivity {

    ListView l;
    Button btn1;
    String url = "http://192.168.1.104:3000/users";
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Integer> list1 = new ArrayList<>();
    ArrayList<Integer> list2 = new ArrayList<>();
    int ind;
    Bundle data;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_list);

        data = getIntent().getExtras();
        message = data.getString("message");

        l = (ListView)findViewById(R.id.allEmpList);
        btn1 = (Button)findViewById(R.id.btn1);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    JSONObject jsonObject;

                    int len = response.length();
                    for (int i=0;i<len;i++){
                        //  jsonObject = response.getJSONObject(i);
                        list.add(response.getJSONObject(i).getString("name"));
                        list1.add(response.getJSONObject(i).getInt("id"));
                    }

                    l.setChoiceMode(l.CHOICE_MODE_MULTIPLE);
                    l.setTextFilterEnabled(true);

                    ArrayAdapter<String> adapter =new ArrayAdapter<>(
                            getApplicationContext(),android.R.layout.simple_list_item_checked,list);

                    l.setAdapter(adapter);

                    l.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    CheckedTextView item = (CheckedTextView) view;
                                    if (item.isChecked()){
                                        list2.add(position);
                                    }
                                    else{
                                        for (int i = 0 ; i < list2.size() ; i++){
                                            if (list2.get(i) == position){
                                                list2.remove(i);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                    );



                    btn1.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int i = 0 ; i < list2.size() ; i++){
                                        //list2.add(i,list1.get(list2.get(i)));

                                        ind = list2.get(i);
                                        //   Log.d("Tag1 : ",Integer.toString(ind));
                                        ind = list1.get(ind);
                                        //   Log.d("Tag2 : ",Integer.toString(ind));
                                        list2.remove(i);
                                        //   Log.d("Tag3 : ",Integer.toString(ind));
                                        list2.add(i,ind);
                                        Log.d("Tag4 : ",Integer.toString(ind));
                                    }

                                    int[] array = new int[list2.size()];
                                    for(int i = 0; i < list2.size(); i++){
                                        array[i] = list2.get(i);
                                    }

                                    Log.d("Tag5 : ", Arrays.toString(array));
                                    // check array here

                                    Intent i = new Intent(CheckedList.this,SentMessage.class);
                                    i.putExtra("uid",array);
                                    i.putExtra("message",message);
                                    startActivity(i);
                                }
                            }
                    );

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
        AppSingleton.getInstance(CheckedList.this).addToRequestQueue(jsonArrayRequest);
    }
}
