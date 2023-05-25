package com.eftar.eftarapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;


import com.eftar.eftarapplication.adapter.YoutubeRecyclerViewAdapter;
import com.eftar.eftarapplication.app.MyApplication;
import com.eftar.eftarapplication.model.Video;

import com.google.firebase.database.DatabaseReference;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;


import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;


public class MainActivity extends AppCompatActivity {
    private EditText searchBar;
    private Button btnMore;
    private RecyclerView video_recView, rank_recView;
    private ImageView asc_btn;
    List<Video> videoList = new ArrayList<>();
    //int size = 0;
    boolean doubleBackToExitPressedOnce = false;
    MyApplication myApplication;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private AsyncHttpClient client;
    private DatabaseReference myRef;
    private Workbook workbook;

    private List<String> issue_code,issue_name,market_type,bond_type,residual_maturity,credit_rating,price_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApplication = (MyApplication) this.getApplication();
        videoList.clear();
        videoList.addAll(myApplication.getVideoList());


        String url = "https://docs.google.com/spreadsheets/d/1AFahxOtDLS5T9lahyU6TIm1AEmRP8wud/edit?usp=sharing&ouid=102552447070770472436&rtpof=true&sd=true";

        searchBar = findViewById(R.id.searchBar);
        btnMore = findViewById(R.id.btnMore);
        asc_btn = findViewById(R.id.asc_btn);
        video_recView = findViewById(R.id.video_recView);
        rank_recView = findViewById(R.id.rank_recView);


        client = new AsyncHttpClient();
        issue_name = new ArrayList<>();

        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(MainActivity.this, "Could not download", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {


                WorkbookSettings ws = new WorkbookSettings();

                ws.setGCDisabled(true);

                if(file != null){
                    //Toast.makeText(MainActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);

                        Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();

                        for(int i=0; i <sheet.getRows();i++){
                            Cell[] row = sheet.getRow(i);
                            issue_name.add(row[1].getContents());


                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }
                }

            }
        });



        layoutManager = new LinearLayoutManager(getApplicationContext());
        video_recView.setLayoutManager(layoutManager);
        myAdapter = new YoutubeRecyclerViewAdapter(videoList, getApplicationContext());
        video_recView.setAdapter(myAdapter);


        asc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked Ascending", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_info), Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }




}