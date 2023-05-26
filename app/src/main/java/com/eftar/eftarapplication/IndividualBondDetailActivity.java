package com.eftar.eftarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eftar.eftarapplication.app.MyApplication;
import com.eftar.eftarapplication.grapgh.CircleView;
import com.eftar.eftarapplication.model.Bond;

import java.util.ArrayList;
import java.util.List;

public class IndividualBondDetailActivity extends AppCompatActivity {
    private AutoCompleteTextView searchBar;

    List<Bond> bondList = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    MyApplication myApplication;
    private ImageView btnSummary;
    private ProgressBar progressBar;
    private List<String> suggestionList = new ArrayList<>();
    String[] suggests2;
    private ArrayAdapter<String> adapter;
    private String is_name;
    int i = 0;

    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_bond_detail);

        myApplication = (MyApplication) this.getApplication();

        searchBar = (AutoCompleteTextView) findViewById(R.id.searchBar);
        btnSummary = findViewById(R.id.btnSummary);
        Intent intent = getIntent();


        if(intent.getStringExtra("is_name") != null){
            is_name = intent.getStringExtra("is_name");
        }



       btnSummary.setOnClickListener(e ->{
           Intent in = new Intent(this, BondSummaryActivity.class);
           in.putExtra("is_name",is_name);
           startActivity(in);
       });

        progressBar = findViewById(R.id.progress_bar);

        score = 75;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // set the limitations for the numeric
                // text under the progress bar
                if (i <= score) {
                    progressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 0);
                } else {
                    handler.removeCallbacks(this);
                }
            }
        }, 0);



        Toast.makeText(myApplication, is_name+"", Toast.LENGTH_SHORT).show();



        myApplication.getBondList(new MyApplication.BondListCallback() {
            @Override
            public void onBondListAvailable(List<Bond> bonds) {

                bondList.addAll(bonds);
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

    }
}