package com.eftar.eftarapplication.app;

import android.app.Application;

import com.eftar.eftarapplication.model.Video;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static List<Video> videoList = new ArrayList<>();



    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        MyApplication.videoList.addAll(videoList);
    }
}
