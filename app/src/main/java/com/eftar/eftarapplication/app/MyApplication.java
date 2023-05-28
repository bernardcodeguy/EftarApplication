package com.eftar.eftarapplication.app;

import android.app.Application;
import android.widget.Toast;

import com.eftar.eftarapplication.R;
import com.eftar.eftarapplication.model.Bond;
import com.eftar.eftarapplication.model.Video;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application{
    private static List<Video> videoList = new ArrayList<>();
    private static List<Bond> bondList = new ArrayList<>();
    String excelUrlEng = "https://github.com/bernardcodeguy/EftarApplication/raw/main/app/src/main/res/file.xls";
    String excelUrlKor = "https://github.com/bernardcodeguy/EftarApplication/raw/main/app/src/main/res/filek.xls";
    String url;
    private AsyncHttpClient client;
    private Workbook workbook;
    public List<Video> getVideoList() {
        return videoList;
    }
    public void setVideoList(List<Video> videoList) {
        MyApplication.videoList.addAll(videoList);
    }



    public void getBondList(final BondListCallback callback) {

        String currentLanguageCode = getResources().getConfiguration().locale.getLanguage();

        // Check if the current language is English
        if (currentLanguageCode.equals("en")) {
            client = new AsyncHttpClient();

            client.get(excelUrlEng, new FileAsyncHttpResponseHandler(getApplicationContext()) {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                    callback.onBondListError();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    WorkbookSettings ws = new WorkbookSettings();

                    ws.setGCDisabled(true);

                    if(file != null){
                        try {
                            workbook = workbook.getWorkbook(file);
                            Sheet sheet = workbook.getSheet(0);

                            bondList.clear();
                            for(int i=0; i <sheet.getRows();i++){

                                Cell[] row = sheet.getRow(i);

                                boolean containsOnlyNumerals = row[0].getContents().matches("\\d+");

                                if (!containsOnlyNumerals) {
                                    continue;
                                }

                                Bond bond = new Bond();
                                bond.setRank(Integer.parseInt(row[0].getContents()));
                                bond.setIs_code(row[1].getContents());
                                bond.setIs_name(row[2].getContents());
                                bond.setMarket_type(row[3].getContents());
                                bond.setBond_type(row[4].getContents());
                                bond.setPrice(convertToDouble(row[5].getContents()));

                                if(!row[6].getContents().isEmpty()){
                                    bond.setChange(convertToDouble(row[6].getContents()));
                                }

                                bond.setReturns(convertToDouble(row[7].getContents()));
                                bond.setTrading_volume(convertToDouble(row[8].getContents()));
                                bond.setTrading_value(convertToDouble(row[9].getContents()));
                                bond.setRes_maturity(row[10].getContents());
                                bond.setCredit_rating(row[11].getContents());
                                bond.setListed_amt(convertToDouble(row[12].getContents()));

                                bondList.add(bond);

                            /*myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    myRef.child(row[0].getContents()).child("rank").setValue(row[0].getContents());
                                    myRef.child(row[0].getContents()).child("is_code").setValue(row[1].getContents());
                                    myRef.child(row[0].getContents()).child("is_name").setValue(row[2].getContents());
                                    myRef.child(row[0].getContents()).child("market_type").setValue(row[3].getContents());
                                    myRef.child(row[0].getContents()).child("bond_type").setValue(row[4].getContents());
                                    myRef.child(row[0].getContents()).child("price").setValue(row[5].getContents());
                                    myRef.child(row[0].getContents()).child("change").setValue(row[6].getContents());
                                    myRef.child(row[0].getContents()).child("returns").setValue(row[7].getContents());
                                    myRef.child(row[0].getContents()).child("trading_volume").setValue(row[8].getContents());
                                    myRef.child(row[0].getContents()).child("trading_value").setValue(row[9].getContents());
                                    myRef.child(row[0].getContents()).child("res_maturity").setValue(row[10].getContents());
                                    myRef.child(row[0].getContents()).child("crdeit_rating").setValue(row[11].getContents());
                                    myRef.child(row[0].getContents()).child("listed_amt").setValue(row[12].getContents());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });*/


                            }
                            callback.onBondListAvailable(bondList);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (BiffException e) {
                            e.printStackTrace();
                        }
                    }

                }

            });
        }else{
            client = new AsyncHttpClient();

            client.get(excelUrlKor, new FileAsyncHttpResponseHandler(getApplicationContext()) {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    Toast.makeText(getApplicationContext(), "Your device might not be connected to internet", Toast.LENGTH_SHORT).show();
                    callback.onBondListError();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    WorkbookSettings ws = new WorkbookSettings();

                    ws.setGCDisabled(true);

                    if(file != null){
                        try {
                            workbook = workbook.getWorkbook(file);
                            Sheet sheet = workbook.getSheet(0);

                            bondList.clear();
                            for(int i=0; i <sheet.getRows();i++){

                                Cell[] row = sheet.getRow(i);

                                boolean containsOnlyNumerals = row[0].getContents().matches("\\d+");

                                if (!containsOnlyNumerals) {
                                    continue;
                                }

                                Bond bond = new Bond();
                                bond.setRank(Integer.parseInt(row[0].getContents()));
                                bond.setIs_code(row[1].getContents());
                                bond.setIs_name(row[2].getContents());
                                bond.setMarket_type(row[3].getContents());
                                bond.setBond_type(row[4].getContents());
                                bond.setPrice(convertToDouble(row[5].getContents()));

                                if(!row[6].getContents().isEmpty()){
                                    bond.setChange(convertToDouble(row[6].getContents()));
                                }

                                bond.setReturns(convertToDouble(row[7].getContents()));
                                bond.setTrading_volume(convertToDouble(row[8].getContents()));
                                bond.setTrading_value(convertToDouble(row[9].getContents()));
                                bond.setRes_maturity(row[10].getContents());
                                bond.setCredit_rating(row[11].getContents());
                                bond.setListed_amt(convertToDouble(row[12].getContents()));

                                bondList.add(bond);
                            }
                            callback.onBondListAvailable(bondList);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (BiffException e) {
                            e.printStackTrace();
                        }
                    }

                }

            });
        }
    }


    private double convertToDouble(String value){
        String cleanString = value.replace(",", "");
        return Double.parseDouble(cleanString);
    }




    public interface BondListCallback {
        void onBondListAvailable(List<Bond> bondList);
        void onBondListError();
    }
}
