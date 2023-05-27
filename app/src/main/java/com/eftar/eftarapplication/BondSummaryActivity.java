package com.eftar.eftarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eftar.eftarapplication.app.MyApplication;
import com.eftar.eftarapplication.model.Bond;

import java.util.ArrayList;
import java.util.List;

public class BondSummaryActivity extends AppCompatActivity {
    private AutoCompleteTextView searchBar;
    private ImageView btnItemDetails;
    private TextView txtBondName,txtIsCode,txtMarketType,txtPriceClose,txtBondType,txtReturn,txtTradingVolume;
    private TextView txtTradingValue,txtResMaturity,txtCreditRating,txtListedAmount;





    List<Bond> bondList = new ArrayList<>();
    MyApplication myApplication;
    private List<String> suggestionList = new ArrayList<>();
    String[] suggests2;
    private ArrayAdapter<String> adapter;
    private String is_name;
    private Bond bond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond_summary);
        Intent intent = getIntent();


        if(intent.getStringExtra("is_name") != null){
            is_name = intent.getStringExtra("is_name");
        }

        myApplication = (MyApplication) this.getApplication();


        txtBondName = findViewById(R.id.txtBondName);
        txtIsCode = findViewById(R.id.txtIsCode);
        txtMarketType = findViewById(R.id.txtMarketType);
        txtPriceClose = findViewById(R.id.txtPriceClose);
        txtBondType = findViewById(R.id.txtBondType);
        txtReturn = findViewById(R.id.txtReturn);
        txtTradingVolume = findViewById(R.id.txtTradingVolume);
        txtTradingValue = findViewById(R.id.txtTradingValue);
        txtResMaturity = findViewById(R.id.txtResMaturity);
        txtCreditRating = findViewById(R.id.txtCreditRating);
        txtListedAmount = findViewById(R.id.txtListedAmount);


        searchBar = (AutoCompleteTextView) findViewById(R.id.searchBar);
        btnItemDetails = findViewById(R.id.btnItemDetails);



        // Set the item click listener to handle selected suggestion
        searchBar.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSuggestion = (String) parent.getItemAtPosition(position);
            Intent in = new Intent(this, IndividualBondDetailActivity.class);
            in.putExtra("is_name",selectedSuggestion);
            startActivity(in);
        });



        btnItemDetails.setOnClickListener(e ->{
            Intent in = new Intent(this, IndividualBondDetailActivity.class);
            in.putExtra("is_name",is_name);
            startActivity(in);
        });

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

                if(bond != null){
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    txtBondName.setText(bond.getIs_name());
                    txtIsCode.setText(bond.getIs_code());
                    txtMarketType.setText(bond.getMarket_type());
                    txtPriceClose.setText(decimalFormat.format(bond.getPrice()));
                    txtBondType.setText(String.valueOf(bond.getBond_type()));

                    txtReturn.setText(String.valueOf(bond.getReturns()));
                    txtTradingVolume.setText(decimalFormat.format(bond.getTrading_volume()));
                    txtTradingValue.setText(decimalFormat.format(bond.getTrading_value()));
                    txtResMaturity.setText(String.valueOf(bond.getRes_maturity()));
                    txtCreditRating.setText(bond.getCredit_rating());
                    txtListedAmount.setText(decimalFormat.format(bond.getListed_amt()));
                }

            }

            @Override
            public void onBondListError() {
                Toast.makeText(myApplication, "Error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}