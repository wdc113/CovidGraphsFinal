package edu.dccc.csv.model;

public class DailyCovid19CasesDataModel {

    private String date;
    private String cases;
    private String country;
    private String countryCode;

    public void setDate(String date) {
        this.date = date;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDate() {
        return date;
    }

    public String getCases() {
        return cases;
    }

    public String getCountryCode() {
        return countryCode;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryCode(String countryCode) {this.countryCode = countryCode; }


    @Override
    public String toString() {
        return "\nCountry=" + getCountry() + "::CountryCode=" + getCountryCode()  + "::Date=" + getDate() + "::Cases " + getCases();
    }
}