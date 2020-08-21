package edu.dccc.csv;

import edu.dccc.csv.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReadCSVWithScanner {


    public ArrayList<UnemploymentDataModel> GetUnemploymentDataSet(String fileName, boolean hasHeader) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        // read file line by line
        String line = null;
        Scanner scanner = null;
        int index = 0;
        ArrayList<UnemploymentDataModel> list = new ArrayList();
        int count = 0;

        while ((line = reader.readLine()) != null) {
            UnemploymentDataModel dp = new UnemploymentDataModel();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");

            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    dp.setDate(data);
                else if (index == 1)
                    dp.setRate(data);
                else
                    System.out.println("invalid data::" + data);
                index++;
            }
            index = 0;
            // Skip the first line of file if file has a header
            if (hasHeader && count == 0) {
                count++;
                continue;
            }
            list.add(dp);
            count++;
        }
        //close reader
        reader.close();

        return list;
    }


    public ArrayList<PopulationDataModel> GetPopulationDataSet(String fileName, boolean hasHeader) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        // read file line by line
        String line = null;
        Scanner scanner = null;
        int index = 0;
        ArrayList<PopulationDataModel> list = new ArrayList();
        int count = 0;

        while ((line = reader.readLine()) != null) {
            PopulationDataModel dp = new PopulationDataModel();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");

            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    dp.setYear(data);
                else if (index == 1)
                    dp.setPopulation(data);
                else
                    System.out.println("invalid data::" + data);
                index++;
            }
            index = 0;
            // Skip the first line of file if file has a header
            if (hasHeader && count == 0) {
                count++;
                continue;
            }
            list.add(dp);
            count++;
        }
        //close reader
        reader.close();

        return list;
    }


    public ArrayList<Covid19_DataModel> GetCovid19DataSet(String fileName, boolean hasHeader) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        // read file line by line
        String line = null;
        Scanner scanner = null;
        int index = 0;
        ArrayList<Covid19_DataModel> list = new ArrayList();
        int count = 0;

        while ((line = reader.readLine()) != null) {
            Covid19_DataModel dp = new Covid19_DataModel();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");

            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    dp.setDate(data);
                else if (index == 1)
                    dp.setCases(data);
                else if (index == 2)
                    dp.setHospitalizations(data);
                else if (index == 3)
                    dp.setDeaths(data);
                else
                    System.out.println("invalid data::" + data);
                index++;
            }
            index = 0;
            // Skip the first line of file if file has a header
            if (hasHeader && count == 0) {
                count++;
                continue;
            }
            list.add(dp);
            count++;
        }
        //close reader
        reader.close();
        return list;
    }


    public ArrayList<Employee> GetEmployeeDataSet(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        // read file line by line
        String line = null;
        Scanner scanner = null;
        int index = 0;
        ArrayList<Employee> list = new ArrayList();

        while ((line = reader.readLine()) != null) {
            Employee emp = new Employee();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    emp.setId(Integer.parseInt(data));
                else if (index == 1)
                    emp.setName(data);
                else if (index == 2)
                    emp.setRole(data);
                else if (index == 3)
                    emp.setSalary(data);
                else
                    System.out.println("invalid data::" + data);
                index++;
            }
            index = 0;
            list.add(emp);
        }

        //close reader
        reader.close();
        return list;
    }

    public ArrayList<DailyCovid19CasesDataModel> GetDailyCovid19CasesDataSet(String fileName, boolean hasHeader) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        // read file line by line
        String line = null;
        Scanner scanner = null;
        int index = 0;
        ArrayList<DailyCovid19CasesDataModel> list = new ArrayList();
        int count = 0;

        while ((line = reader.readLine()) != null) {
            DailyCovid19CasesDataModel dp = new DailyCovid19CasesDataModel();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();

                if (index == 0)
                    dp.setCountry(data);
                else if (index == 1)
                    dp.setCountryCode(data);
                else if (index == 2)
                    dp.setDate(data);
                else if (index == 3)
                    dp.setCases(data);
                else
                  System.err.println("invalid data::" + data);
                index++;
            }
            index = 0;
            // Skip the first line of file if file has a header
            if (hasHeader && count == 0) {
                count++;
                continue;
            }
            list.add(dp);
            count++;
        }
        //close reader
        reader.close();
        return list;
    }

    public static void main(String[] args) throws IOException {
        // open file input stream
        ReadCSVWithScanner readCSVWithScanner = new ReadCSVWithScanner();

       ArrayList<Employee> empList = readCSVWithScanner.GetEmployeeDataSet("resources/employees.csv");
        System.out.println(empList);
        ArrayList<UnemploymentDataModel> unemploymentList = readCSVWithScanner.GetUnemploymentDataSet("resources/UnemploymentDataUS.csv", true);
        System.out.println(unemploymentList);
        ArrayList<Covid19_DataModel> NYCcovid19List = readCSVWithScanner.GetCovid19DataSet("resources/NYCCovid19Data.csv", true);
        System.out.println(NYCcovid19List);
        ArrayList<DailyCovid19CasesDataModel> covid19Cases = readCSVWithScanner.GetDailyCovid19CasesDataSet("resources/daily-cases-covid-19.csv", true);
        List<DailyCovid19CasesDataModel> usList = covid19Cases
                     .stream()
                     .filter(euData -> euData.getCountry().equals("United States"))
                     .collect(Collectors.toList());
        List<DailyCovid19CasesDataModel> euList = covid19Cases
                .stream()
                .filter(euData -> euData.getCountry().equals("European Union"))
                .collect(Collectors.toList());

        ArrayList<PopulationDataModel> populationList = readCSVWithScanner.GetPopulationDataSet("resources/WorldPopulation.csv", true);
        System.out.println(populationList);


        System.out.println(usList);
        System.out.println(euList);
    }

}