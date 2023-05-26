package com.eftar.eftarapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;


import com.eftar.eftarapplication.adapter.RankRecyclerViewAdapter;
import com.eftar.eftarapplication.adapter.YoutubeRecyclerViewAdapter;
import com.eftar.eftarapplication.app.MyApplication;
import com.eftar.eftarapplication.model.Bond;
import com.eftar.eftarapplication.model.Video;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;


import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView searchBar;
    private Button btnMore;
    private RecyclerView video_recView, rank_recView;
    private ImageView asc_btn;
    List<Video> videoList = new ArrayList<>();
    List<Bond> bondList = new ArrayList<>();
    //int size = 0;
    boolean doubleBackToExitPressedOnce = false;
    MyApplication myApplication;
    private RecyclerView.Adapter youtubeAdapter,rankAdapter;
    private RecyclerView.LayoutManager youtubeLayoutManager,rankLayoutManager;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://eftar-be95a-default-rtdb.firebaseio.com/").child("eng");

    private int numToShow = 5;

    private List<String> suggestionList = new ArrayList<>();

    /*String[] suggests = { "Paries,France", "PA,United States","Parana,Brazil",
            "Padua,Italy", "Pasadena,CA,United States"};*/
    String[] suggests2;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApplication = (MyApplication) this.getApplication();
        videoList.clear();
        videoList.addAll(myApplication.getVideoList());

        String currentLanguageCode = getResources().getConfiguration().locale.getLanguage();

        // Check if the current language is English
        /*if (currentLanguageCode.equals("en")) {
            Toast.makeText(myApplication, "English selected", Toast.LENGTH_SHORT).show();
        } */

        searchBar = (AutoCompleteTextView) findViewById(R.id.searchBar);
        btnMore = findViewById(R.id.btnMore);
        asc_btn = findViewById(R.id.asc_btn);
        video_recView = findViewById(R.id.video_recView);
        rank_recView = findViewById(R.id.rank_recView);


        // Show Ranking
        rankLayoutManager = new LinearLayoutManager(getApplicationContext());
        rank_recView.setLayoutManager(rankLayoutManager);
        rankAdapter = new RankRecyclerViewAdapter(bondList, getApplicationContext(),numToShow);
        rank_recView.setAdapter(rankAdapter);




        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numToShow > 5){

                    btnMore.setText(R.string.more);
                    numToShow = 5; // Update the number of items to show

                    // Show Ranking
                    rankLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rank_recView.setLayoutManager(rankLayoutManager);
                    rankAdapter = new RankRecyclerViewAdapter(bondList, getApplicationContext(),numToShow);
                    rank_recView.setAdapter(rankAdapter);

                    // Notify the adapter of the data set change
                    rankAdapter.notifyDataSetChanged();


                }else{

                    btnMore.setText(R.string.less);
                    numToShow = 10; // Update the number of items to show

                    // Show Ranking
                    rankLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rank_recView.setLayoutManager(rankLayoutManager);
                    rankAdapter = new RankRecyclerViewAdapter(bondList, getApplicationContext(),numToShow);
                    rank_recView.setAdapter(rankAdapter);

                    // Notify the adapter of the data set change
                    rankAdapter.notifyDataSetChanged();

                }


            }
        });



        // Set the item click listener to handle selected suggestion
        searchBar.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSuggestion = (String) parent.getItemAtPosition(position);
            Toast.makeText(MainActivity.this, "Selected: " + selectedSuggestion, Toast.LENGTH_SHORT).show();
        });



        myApplication.getBondList(new MyApplication.BondListCallback() {
            @Override
            public void onBondListAvailable(List<Bond> bonds) {



                bondList.addAll(bonds);
                numToShow = 5;
                // Notify the adapter of the data set change
                rankAdapter.notifyDataSetChanged();

                for(Bond b : bondList){
                    suggestionList.add(b.getIs_name());
                }
                suggests2 = suggestionList.toArray(new String[suggestionList.size()]);
                // Create an ArrayAdapter with the suggestions
                adapter = new ArrayAdapter<>(myApplication, android.R.layout.select_dialog_item, suggests2);
                searchBar.setThreshold(1);

                // Set the adapter to the AutoCompleteTextView
                searchBar.setAdapter(adapter);
            }

            @Override
            public void onBondListError() {
                Toast.makeText(myApplication, "Error occured", Toast.LENGTH_SHORT).show();
            }
        });


        // Show Youtube Videos
        youtubeLayoutManager = new LinearLayoutManager(getApplicationContext());
        video_recView.setLayoutManager(youtubeLayoutManager);
        youtubeAdapter = new YoutubeRecyclerViewAdapter(videoList, getApplicationContext());
        video_recView.setAdapter(youtubeAdapter);


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