package com.eftar.eftarapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eftar.eftarapplication.app.MyApplication;
import com.eftar.eftarapplication.model.Video;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity {

    String url = "https://www.googleapis.com/youtube/v3/search";
    String apiKey = "AIzaSyB2EgUv_XCN24zHqmRZvPjC_kbRBirZsj4";
    String query = "South korea economy videos";
    String requestUrl,order;

    private DatabaseReference myRef;

    JsonObjectRequest request;
    List<Video> videoList = new ArrayList<>();
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        myApplication = (MyApplication) this.getApplication();

        order = "rating";

        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://eftar-be95a-default-rtdb.firebaseio.com/");


        // Create a reference to the desired child node
        DatabaseReference engRef = myRef.child("eng");

        // Create a reference to the desired child node
        DatabaseReference korRef = myRef.child("kor");




        requestUrl = url + "?part=snippet&maxResults=" + 3 + "&q=" + query +"&order="+order+ "&key=" + apiKey;

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                if(request.hasHadResponseDelivered()){
                    DatabaseReference fl = engRef.child("test");

                    DatabaseReference fl2 = korRef.child("test");

                    fl.setValue("null");

                    fl2.setValue("null");
                    myApplication.setVideoList(videoList);
                    //Toast.makeText(SplashActivity.this, myApplication.getVideoList().size()+" splash videos", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                request = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray items = response.getJSONArray("items");

                                    for (int i = 0; i < items.length(); i++) {
                                        Video video = new Video();
                                        JSONObject videoItem = items.getJSONObject(i);
                                        JSONObject snippet = videoItem.getJSONObject("snippet");
                                        String videoTitle = snippet.getString("title");
                                        JSONObject id = videoItem.getJSONObject("id");
                                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                                        JSONObject defaults = thumbnails.getJSONObject("default");
                                        String url = defaults.getString("url");
                                        String channelTitle = snippet.getString("channelTitle");
                                        String videoId = id.getString("videoId");

                                        video.setVideoId(videoId);
                                        video.setTitle(videoTitle);
                                        video.setUrl(url);
                                        video.setChannelTitle(channelTitle);

                                        videoList.add(video);
                                    }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "Error: " + error.getMessage());
                            }
                        });

                queue.add(request);
            }
        }).start();

    }
}