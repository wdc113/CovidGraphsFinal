package edu.dccc.csv;

import edu.dccc.csv.model.PopulationDataModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WorldPopulationTimeSeries_JFrame extends JFrame {

    public WorldPopulationTimeSeries_JFrame(final String title ) {
        super( title );
        final XYDataset dataset = createDataset( );
        final JFreeChart chart = createChart( dataset );

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy"));
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 370 ) );
        chartPanel.setMouseZoomable( true , false );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( ) {
        TimeSeries series = new TimeSeries("World population 1950-2020 (Billions)");
        ArrayList<PopulationDataModel> populationList;
        ReadCSVWithScanner readCSVWithScanner = new ReadCSVWithScanner();
        try {
            populationList = readCSVWithScanner.GetPopulationDataSet("resources/WorldPopulation.csv", true);

            for (PopulationDataModel dp : populationList) {
                try {
                    Date date = new SimpleDateFormat("yyyy").parse(dp.getYear());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    Day day = new Day(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                    long pop = Long.parseLong(dp.getPopulation().replaceAll(" ", ""));
                    Number value = Double.valueOf(pop/1000);
                    series.add(day, value);
                } catch (Exception e) {
                    System.err.println("Problem loading dataset");
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("IoException");
        }
        return new TimeSeriesCollection(series);
       }


    private JFreeChart createChart( XYDataset dataset ) {
        return ChartFactory.createTimeSeriesChart(
                "World Population",
                "Year",
                "Population (millions)",
                dataset,
                false,
                false,
                false);
    }

    public static void main( final String[ ] args ) {
        final String title = "World Population Growth";
        final WorldPopulationTimeSeries_JFrame ts = new WorldPopulationTimeSeries_JFrame( title );
        ts.pack( );
     //   RefineryUtilities.positionFrameRandomly( ts );
        ts.setVisible( true );
    }
}