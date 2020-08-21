package edu.dccc.csv.model;

public class PopulationDataModel {

    private String year;
    private String population;

    public void setYear(String year) {
        this.year = year;
    }
    public void setPopulation(String population) {
        this.population = population;
    }
    public String getPopulation() {
        return population;
    }
    public String getYear() {
        return year;
    }

    @Override
    public String toString(){
        return "\nYear="+getYear()+"::Population"+ getPopulation();
    }
}