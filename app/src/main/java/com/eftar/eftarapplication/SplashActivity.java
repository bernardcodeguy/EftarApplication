package com.eftar.eftarapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
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
import com.loopj.android.http.FileAsyncHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class SplashActivity extends AppCompatActivity {

    private Button btnClose;
    private String url = "https://www.googleapis.com/youtube/v3/search";
    private String apiKey = "AIzaSyB2EgUv_XCN24zHqmRZvPjC_kbRBirZsj4";
    private String query = "South korea economy videos";
    private String requestUrl, order,regionCode,relevanceLanguage;
    private AsyncHttpClient client;
    private DatabaseReference myRefEng = FirebaseDatabase.getInstance().getReferenceFromUrl("https://eftar-be95a-default-rtdb.firebaseio.com/").child("eng");
    private DatabaseReference myRefKor = FirebaseDatabase.getInstance().getReferenceFromUrl("https://eftar-be95a-default-rtdb.firebaseio.com/").child("kor");
    private Workbook workbook;
    String excelUrlEng = "https://github.com/bernardcodeguy/EftarApplication/raw/main/app/src/main/res/file.xls";
    String excelUrlKor = "https://github.com/bernardcodeguy/EftarApplication/raw/main/app/src/main/res/filek.xls";
    String urlForExcel;
    private RequestQueue queue;
    JsonObjectRequest request;
    List<Video> videoList = new ArrayList<>();

    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btnClose = findViewById(R.id.btnClose);

        if (isNetworkAvailable()) {
            btnClose.setVisibility(View.GONE);

            myApplication = (MyApplication) this.getApplication();

            String currentLanguageCode = getResources().getConfiguration().locale.getLanguage();
            regionCode = "KR";

            // Check if the current language is English
            if (currentLanguageCode.equals("en")) {
                relevanceLanguage = "en";
            }else{
                relevanceLanguage = "ko";
            }

            order = "date";

            requestUrl = url + "?part=snippet&maxResults=" + 3 + "&q=" + query +"&regionCode="+regionCode+"&relevanceLanguage="+relevanceLanguage+"&order="+order+ "&key=" + apiKey;

            queue = Volley.newRequestQueue(this);

            queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    if(request.hasHadResponseDelivered()){
                        myApplication.getVideoList().clear();
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

        } else {

            Toast.makeText(myApplication, R.string.no_internet, Toast.LENGTH_SHORT).show();

            btnClose.setVisibility(View.VISIBLE);

            btnClose.setOnClickListener(e -> {
                finishAffinity();
                System.exit(0);
            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

 }