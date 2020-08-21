package edu.dccc.csv.model;

public class Covid19_DataModel {

    private String date;
    private String cases;
    private String hospitalizations;
    private String deaths;

    public void setDate(String date) {
        this.date = date;
    }
    public void setHospitalizations(String hospitalizations) {
        this.hospitalizations = hospitalizations;
    }
    public void setCases(String cases) {
        this.cases = cases;
    }
    public void setDeaths(String deaths) { this.deaths = deaths;    }

    public String getDeaths() {
        return deaths;
    }
    public String getDate() {
        return date;
    }
    public String getCases() {
        return cases;
    }
    public String getHospitalizations() {
        return hospitalizations;
    }

    @Override
    public String toString(){
        return "\nDate="+getDate()+"::Cases "+ getCases()+"::Hospitalizations"+getHospitalizations()+"::Deaths " +getDeaths();
    }
}