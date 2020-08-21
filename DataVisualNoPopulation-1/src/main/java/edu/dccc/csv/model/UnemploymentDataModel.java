package edu.dccc.csv.model;

public class UnemploymentDataModel {

    private String date;
    private String rate;

    public void setDate(String date) {
        this.date = date;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public String getRate() {
        return rate;
    }
    public String getDate() {
        return date;
    }

    @Override
    public String toString(){
        return "\nDate="+getDate()+"::Unemployment Rate"+ getRate();
    }
}