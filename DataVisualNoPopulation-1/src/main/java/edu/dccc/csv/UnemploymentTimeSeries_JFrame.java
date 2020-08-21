package edu.dccc.csv;

import edu.dccc.csv.model.UnemploymentDataModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
//import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UnemploymentTimeSeries_JFrame extends JFrame {

    public UnemploymentTimeSeries_JFrame(final String title ) {
        super( title );
        final XYDataset dataset = createDataset( );
        final JFreeChart chart = createChart( dataset );

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-yyyy"));
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 370 ) );
        chartPanel.setMouseZoomable( true , false );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( ) {
        TimeSeries series = new TimeSeries("Monthly US Unemployment 1940-2020");
        ArrayList<UnemploymentDataModel> unemploymentList;
        ReadCSVWithScanner readCSVWithScanner = new ReadCSVWithScanner();
        try {
            unemploymentList = readCSVWithScanner.GetUnemploymentDataSet("resources/UnemploymentDataUS.csv", true);

            for (UnemploymentDataModel dp : unemploymentList) {
                try {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dp.getDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    Day day = new Day(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                    Number value = Double.valueOf(dp.getRate());
                    series.add(day, value);
                } catch (Exception e) {
                    System.err.println("Prolbem loading dataset");
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
                "Monthly Unemployment US",
                "Month-Year",
                "Unemployment Rate",
                dataset,
                false,
                false,
                false);
    }

    public static void main( final String[ ] args ) {
        final String title = "US Unemployment Rate";
        final UnemploymentTimeSeries_JFrame ts = new UnemploymentTimeSeries_JFrame( title );
        ts.pack( );
     //   RefineryUtilities.positionFrameRandomly( ts );
        ts.setVisible( true );
    }
}