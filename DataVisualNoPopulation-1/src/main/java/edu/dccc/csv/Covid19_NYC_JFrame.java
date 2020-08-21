package edu.dccc.csv;

import edu.dccc.csv.model.Covid19_DataModel;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


public class Covid19_NYC_JFrame extends JFrame {

    public Covid19_NYC_JFrame(final String title ) {
        super( title );
        final XYDataset dataset = createDataset( );
        final JFreeChart chart = createChart( dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new Dimension( 590 , 370 ) );
        chartPanel.setMouseZoomable( true , false );

        setContentPane( chartPanel );
    }

    private XYDataset createDataset() {
        TimeSeries s1 = new TimeSeries( "Cases");
        TimeSeries s2 = new TimeSeries( "Hospitalizations");
        TimeSeries s3 = new TimeSeries( "Deaths");

        //     final TimeSeries series = new TimeSeries("Covid19 NYC: Cases, Hospitalizations, Deaths");
        ArrayList<Covid19_DataModel> covid19_dataModelArrayList;
        ReadCSVWithScanner readCSVWithScanner = new ReadCSVWithScanner();
        TimeSeriesCollection dataset = new TimeSeriesCollection();
       try {
           covid19_dataModelArrayList = readCSVWithScanner.GetCovid19DataSet("resources/NYCCovid19Data.csv", true);

           for (Covid19_DataModel dp : covid19_dataModelArrayList)
           {
               try {
                   Date dt = new SimpleDateFormat("MM/dd/yyyy").parse(dp.getDate()) ;
                   Calendar calendar = Calendar.getInstance();
                   calendar.setTime(dt);
                   Day day = new Day(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+ 1, calendar.get(Calendar.YEAR));

                   Number cases = Double.valueOf(dp.getCases());
                   Number hosp = Double.valueOf(dp.getHospitalizations());
                   Number deaths = Double.valueOf(dp.getDeaths());
                   s1.add(day, cases);
                   s2.add(day,hosp);
                   s3.add(day,deaths);
                   dataset.addSeries(s1);
                   dataset.addSeries(s2);
                   dataset.addSeries(s3);
               } catch (Exception e) {
                   System.err.println("Error adding to series");
               }
           }
       }
       catch (IOException e) {
           System.err.println("Error reading dataset");
       }
       return dataset;
    }


    private JFreeChart createChart( XYDataset dataset ) {
        JFreeChart chart =  ChartFactory.createTimeSeriesChart(
                "NYC Daily Covid19 Case Data",
                "Date",
                "Cases,Hospitalizations,& Deaths",
                dataset,
                true,
                true,
                false);
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        // sets paint color for each series

        XYItemRenderer r = plot.getRenderer();


        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
        //    renderer.setBaseShapesVisible(true);
         //   renderer.setBaseShapesFilled(true);
            renderer.setDefaultShapesVisible(true);
            renderer.setDefaultShapesVisible(true);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, Color.GREEN);
            renderer.setSeriesPaint(2, Color.YELLOW);
        }
        LegendItemCollection legend = plot.getLegendItems();
        int count = 0;
        Iterator iterator=legend.iterator();
        while(iterator.hasNext()){
            count++;
            LegendItem item=(LegendItem)iterator.next();
            if(count > 3)
                iterator.remove();
        }
        plot.setFixedLegendItems(legend);

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd-yy"));

        return chart;
    }

    public static void main( final String[ ] args ) {
        final String title = "NYC Covid19 Data";
        final Covid19_NYC_JFrame ts = new Covid19_NYC_JFrame( title );
        ts.pack( );
   //     RefineryUtilities.positionFrameRandomly( ts );
        ts.setVisible( true );
    }
}

//    LineAndShapeRenderer renderer = new LineAndShapeRenderer();
//    CategoryAxis categoryAxis = new CategoryAxis(<categoryAxisLabel>);
//        ValueAxis valueAxis = new NumberAxis(<valueAxisLabel>);
//        CategoryPlot plot = new CategoryPlot(data, categoryAxis, valueAxis, renderer);
//        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);
//// Customization of Chart
//        chart.setBorderVisible(true);
