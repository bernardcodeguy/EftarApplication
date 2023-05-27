package com.eftar.eftarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DecimalFormat;
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
    double principal = 1000000;
    double maturityRate;
    double redemption;
    int score,creditRatingPoint,maturityRatePoint,interestRatePoint;
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

        // Set the item click listener to handle selected suggestion
        searchBar.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSuggestion = (String) parent.getItemAtPosition(position);
            Intent in = new Intent(this, IndividualBondDetailActivity.class);
            in.putExtra("is_name",selectedSuggestion);
            startActivity(in);
        });


       btnSummary.setOnClickListener(e ->{
           Intent in = new Intent(this, BondSummaryActivity.class);
           in.putExtra("is_name",is_name);
           startActivity(in);
       });

        progressBar = findViewById(R.id.progress_bar);


        myApplication.getBondList(new MyApplication.BondListCallback() {
            @Override
            public void onBondListAvailable(List<Bond> bonds) {

                bondList.addAll(bonds);
                // Clear focus from the AutoCompleteTextView
                searchBar.clearFocus();
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

                if(!bond.getIs_name().isEmpty()){
                    txtBondName.setText(bond.getIs_name());
                }

                if(!bond.getCredit_rating().isEmpty()){
                    txtCreditRating.setText(bond.getCredit_rating());


                    if(bond.getCredit_rating().equals("A") || bond.getCredit_rating().equals("A-") || bond.getCredit_rating().equals("A+")){
                        creditRatingPoint = 70;
                    }else if(bond.getCredit_rating().equals("AA") || bond.getCredit_rating().equals("AA-") || bond.getCredit_rating().equals("AA+")){
                        creditRatingPoint = 80;
                    }else if(bond.getCredit_rating().equals("AAA") || bond.getCredit_rating().equals("AAA-") || bond.getCredit_rating().equals("AAA+")){
                        creditRatingPoint = 100;
                    }else if(bond.getCredit_rating().equals("BBB") || bond.getCredit_rating().equals("BBB-") || bond.getCredit_rating().equals("BBB+")){
                        creditRatingPoint = 60;
                    }else if(bond.getCredit_rating().equals("BB") || bond.getCredit_rating().equals("BB-") || bond.getCredit_rating().equals("BB+")){
                        creditRatingPoint = 50;
                    }else if(bond.getCredit_rating().equals("B") || bond.getCredit_rating().equals("B-") || bond.getCredit_rating().equals("B+")){
                        creditRatingPoint = 40;
                    }else if(bond.getCredit_rating().equals("CCC") || bond.getCredit_rating().equals("CCC-") || bond.getCredit_rating().equals("CCC+")){
                        creditRatingPoint = 30;
                    }else if(bond.getCredit_rating().equals("CC") || bond.getCredit_rating().equals("CC-") || bond.getCredit_rating().equals("CC+")){
                        creditRatingPoint = 20;
                    }else if(bond.getCredit_rating().equals("C") || bond.getCredit_rating().equals("C-") || bond.getCredit_rating().equals("C+")){
                        creditRatingPoint = 10;
                    }

                    txtCreditRatingPoint.setText(String.valueOf(creditRatingPoint));


                    redemption = bond.getReturns() + principal;

                    double maturity = (redemption - principal) / (principal);

                    maturityRate = maturity * 100;

                    // Format the maturityRate to 2 decimal places
                   String formattedMaturityRate = String.format("%.5f", maturityRate);

                    txtMaturityPercentage.setText(String.valueOf(formattedMaturityRate));

                    if(maturityRate >= 5){
                        maturityRatePoint = 100;
                    }else  if(maturityRate >= 4){
                        maturityRatePoint = 80;
                    }else  if(maturityRate >= 3){
                        maturityRatePoint = 70;
                    }else  if(maturityRate >= 2){
                        maturityRatePoint = 60;
                    }else  if(maturityRate >= 1){
                        maturityRatePoint = 40;
                    }else  if(maturityRate < 1){
                        maturityRatePoint = 20;
                    }

                    txtMaturityRatePoint.setText(String.valueOf(maturityRatePoint));

                }


                txtInterestIncomePercentage.setText(String.valueOf(bond.getReturns()));

                if(bond.getReturns() >= 5 ){
                    interestRatePoint = 100;
                }else if(bond.getReturns() < 5  && bond.getReturns() > 4){
                    interestRatePoint = 80;
                }else if(bond.getReturns() < 4  && bond.getReturns() > 3){
                    interestRatePoint = 70;
                }else if(bond.getReturns() < 3  && bond.getReturns() > 2){
                    interestRatePoint = 60;
                }else if(bond.getReturns() < 2  && bond.getReturns() > 1){
                    interestRatePoint = 50;
                }else if(bond.getReturns() < 1){
                    interestRatePoint = 20;
                }

            txtInterestIncomePoint.setText(String.valueOf(interestRatePoint));




                score = (int) ((int) ( maturityRatePoint * 0.4) + (creditRatingPoint * 0.3) + (interestRatePoint * 0.3));

                txtCircScore.setText(String.valueOf(score));

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

            }

            @Override
            public void onBondListError() {
                Toast.makeText(myApplication, "Error occured", Toast.LENGTH_SHORT).show();
            }
        });

    }
}