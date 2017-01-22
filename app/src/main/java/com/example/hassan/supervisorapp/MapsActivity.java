package com.example.hassan.supervisorapp;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> points = new ArrayList<LatLng>();
    private Double[] pointX , pointY;
    private String url = "http://192.168.1.104:3000/locations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    JSONObject jsonObject = new JSONObject();

                    String temp;

                    ArrayList<Double> lat = new ArrayList<>();
                    ArrayList<Double> lon = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++){

                        jsonObject = response.getJSONObject(i);

                        temp = jsonObject.getString("latitude");
                        lat.add(Double.parseDouble(temp));

                        temp = jsonObject.getString("longitude");
                        lon.add(Double.parseDouble(temp));
                    }

                    pointX = lat.toArray(new Double[lat.size()]);
                    pointY = lon.toArray(new Double[lon.size()]);

                    for (int i = 0 ; i < pointX.length; i++){

                        points.add(new LatLng(pointX[i], pointY[i]));
                    }

                    PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);

                    for (int z = 0; z < points.size(); z++) {
                        LatLng point = points.get(z);
                        options.add(point);
                    }

                    mMap.addPolyline(options);

                    LatLng start = new LatLng(pointX[0],pointY[0]);
                    mMap.addMarker(new MarkerOptions().position(start).title("Marker"));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(start));


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
        AppSingleton.getInstance(MapsActivity.this).addToRequestQueue(jsonArrayRequest);

        // Add a marker in Sydney and move the camera
//        LatLng start = new LatLng(24.887332,67.125242);
//        mMap.addMarker(new MarkerOptions().position(start).title("Marker"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(start));

//        double pointX[] = {24.887332,24.894767,24.900723,24.901151,24.899400};
//        double pointY[] = {67.125242,67.119963,67.116101,67.111852,67.106316};
//
//        for (int i = 0 ; i < pointX.length; i++){
//
//            points.add(new LatLng(pointX[i], pointY[i]));
//        }
//
//        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
//
//        for (int z = 0; z < points.size(); z++) {
//            LatLng point = points.get(z);
//            options.add(point);
//        }
//
//        mMap.addPolyline(options);
    }
}
