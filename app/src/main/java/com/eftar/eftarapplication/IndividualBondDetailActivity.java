package com.eftar.eftarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eftar.eftarapplication.app.MyApplication;
import com.eftar.eftarapplication.grapgh.CircleView;
import com.eftar.eftarapplication.model.Bond;

import java.util.ArrayList;
import java.util.List;

public class IndividualBondDetailActivity extends AppCompatActivity {
    private TextView txtBondName,txtCircRating,txtCircScore,txtMaturityRatePoint,txtMaturityPercentage,txtInterestIncomePoint,txtInterestIncomePercentage;
    private TextView txtCreditRatingPoint,txtCreditRating;
    private AutoCompleteTextView searchBar;
    private ImageView btnSummary;
    private ProgressBar progressBar;

    List<Bond> bondList = new ArrayList<>();
    MyApplication myApplication;
    private List<String> suggestionList = new ArrayList<>();
    String[] suggests2;
    private ArrayAdapter<String> adapter;
    private String is_name;
    int i = 0;
    Bond bond;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_bond_detail);

        Intent intent = getIntent();
        if(intent.getStringExtra("is_name") != null){
            is_name = intent.getStringExtra("is_name");
        }

        myApplication = (MyApplication) this.getApplication();

        txtBondName = findViewById(R.id.txtBondName);
        txtCircRating = findViewById(R.id.txtCircRating);
        txtCircScore = findViewById(R.id.txtCircScore);
        txtMaturityRatePoint = findViewById(R.id.txtMaturityRatePoint);
        txtMaturityPercentage = findViewById(R.id.txtMaturityPercentage);
        txtInterestIncomePoint = findViewById(R.id.txtInterestIncomePoint);
        txtInterestIncomePercentage = findViewById(R.id.txtInterestIncomePercentage);
        txtCreditRatingPoint = findViewById(R.id.txtCreditRatingPoint);
        txtCreditRating = findViewById(R.id.txtCreditRating);




        searchBar = (AutoCompleteTextView) findViewById(R.id.searchBar);
        btnSummary = findViewById(R.id.btnSummary);



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


                for(Bond b : bondList){
                    if(b.getIs_name().equals(is_name)){
                        bond = b;
                    }
                }

                Toast.makeText(myApplication, bond.getCredit_rating()+": Credit Rating", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBondListError() {
                Toast.makeText(myApplication, "Error occured", Toast.LENGTH_SHORT).show();
            }
        });

    }
}