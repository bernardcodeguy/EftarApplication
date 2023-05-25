package com.eftar.eftarapplication.background;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YouTubeApiRequest extends AsyncTask<Void, Void, List<String>> {

    private static final String TAG = YouTubeApiRequest.class.getSimpleName();
    private final RequestQueue queue;
    private final String requestUrl;

    public YouTubeApiRequest(RequestQueue queue, String requestUrl) {
        this.queue = queue;
        this.requestUrl = requestUrl;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        final List<String> videoTitles = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0; i < items.length(); i++) {
                                JSONObject videoItem = items.getJSONObject(i);
                                JSONObject snippet = videoItem.getJSONObject("snippet");
                                String videoTitle = snippet.getString("title");
                                videoTitles.add(videoTitle);
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

       /* // Wait for the response to be processed
        while (videoTitles.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        return videoTitles;
    }
}
