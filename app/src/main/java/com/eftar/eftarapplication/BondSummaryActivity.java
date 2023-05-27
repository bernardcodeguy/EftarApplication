package com.eftar.eftarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.eftar.eftarapplication.app.MyApplication;
import com.eftar.eftarapplication.model.Bond;

import java.util.ArrayList;
import java.util.List;

public class BondSummaryActivity extends AppCompatActivity {
    private AutoCompleteTextView searchBar;
    private ImageView btnItemDetails;
    List<Bond> bondList = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    MyApplication myApplication;
    private List<String> suggestionList = new ArrayList<>();
    String[] suggests2;
    private ArrayAdapter<String> adapter;
    private String is_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond_summary);

        myApplication = (MyApplication) this.getApplication();

        searchBar = (AutoCompleteTextView) findViewById(R.id.searchBar);

        btnItemDetails = findViewById(R.id.btnItemDetails);

        Intent intent = getIntent();


        if(intent.getStringExtra("is_name") != null){
            is_name = intent.getStringExtra("is_name");
        }



        btnItemDetails.setOnClickListener(e ->{
            Intent in = new Intent(this, IndividualBondDetailActivity.class);
            in.putExtra("is_name",is_name);
            startActivity(in);
        });

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