package com.eftar.eftarapplication.model;

public class Video {
    private String videoId;
    private String title;
    private String url;
    private String channelTitle;
    private String viewCount;
    
    
    public Video(){
        
    }


    public Video(String videoId, String title, String url, String channelTitle, String viewCount) {
        this.videoId = videoId;
        this.title = title;
        this.url = url;
        this.channelTitle = channelTitle;
        this.viewCount = viewCount;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String channelId) {
        this.videoId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
