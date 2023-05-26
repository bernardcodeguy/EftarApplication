package com.eftar.eftarapplication.model;

public class Bond {
    private int rank;
    private String is_code,is_name,market_type,bond_type,credit_rating,res_maturity;
    private double price,change,returns,trading_volume,trading_value,listed_amt;

    public Bond() {

    }

    public Bond(int rank, String is_code, String is_name, String market_type, String bond_type, String credit_rating, String res_maturity, double price, double change, double returns, double trading_volume, double trading_value, double listed_amt) {
        this.rank = rank;
        this.is_code = is_code;
        this.is_name = is_name;
        this.market_type = market_type;
        this.bond_type = bond_type;
        this.credit_rating = credit_rating;
        this.res_maturity = res_maturity;
        this.price = price;
        this.change = change;
        this.returns = returns;
        this.trading_volume = trading_volume;
        this.trading_value = trading_value;
        this.listed_amt = listed_amt;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getIs_code() {
        return is_code;
    }

    public void setIs_code(String is_code) {
        this.is_code = is_code;
    }

    public String getIs_name() {
        return is_name;
    }

    public void setIs_name(String is_name) {
        this.is_name = is_name;
    }

    public String getMarket_type() {
        return market_type;
    }

    public void setMarket_type(String market_type) {
        this.market_type = market_type;
    }

    public String getBond_type() {
        return bond_type;
    }

    public void setBond_type(String bond_type) {
        this.bond_type = bond_type;
    }

    public String getCredit_rating() {
        return credit_rating;
    }

    public void setCredit_rating(String credit_rating) {
        this.credit_rating = credit_rating;
    }

    public String getRes_maturity() {
        return res_maturity;
    }

    public void setRes_maturity(String res_maturity) {
        this.res_maturity = res_maturity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getReturns() {
        return returns;
    }

    public void setReturns(double returns) {
        this.returns = returns;
    }

    public double getTrading_volume() {
        return trading_volume;
    }

    public void setTrading_volume(double trading_volume) {
        this.trading_volume = trading_volume;
    }

    public double getTrading_value() {
        return trading_value;
    }

    public void setTrading_value(double trading_value) {
        this.trading_value = trading_value;
    }

    public double getListed_amt() {
        return listed_amt;
    }

    public void setListed_amt(double listed_amt) {
        this.listed_amt = listed_amt;
    }
}
