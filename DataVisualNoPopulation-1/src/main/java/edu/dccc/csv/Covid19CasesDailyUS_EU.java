package edu.dccc.csv;

import edu.dccc.csv.model.DailyCovid19CasesDataModel;
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
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;


public class Covid19CasesDailyUS_EU extends JFrame {

    public Covid19CasesDailyUS_EU(final String title ) {
        super( title );
        final XYDataset dataset = createDataset( );
        final JFreeChart chart = createChart( dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new Dimension( 1200 , 800 ) );
        chartPanel.setMouseZoomable( true , false );

        setContentPane( chartPanel );
    }

    private XYDataset createDataset() {
        TimeSeries s1 = new TimeSeries( "Daily Cases US");
        TimeSeries s2 = new TimeSeries( "Daily Cases EU");;

        //     final TimeSeries series = new TimeSeries("Covid19 NYC: Cases, Hospitalizations, Deaths");
        ArrayList<DailyCovid19CasesDataModel> covid19CasesAll;
        ReadCSVWithScanner readCSVWithScanner = new ReadCSVWithScanner();
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        try {
            covid19CasesAll = readCSVWithScanner.GetDailyCovid19CasesDataSet("resources/daily-cases-covid-19.csv", true);
            List<DailyCovid19CasesDataModel> usList = covid19CasesAll
                    .stream()
                    .filter(euData -> euData.getCountry().equals("United States"))
                    .collect(Collectors.toList());
            List<DailyCovid19CasesDataModel> euList = covid19CasesAll
                    .stream()
                    .filter(euData -> euData.getCountry().equals("European Union"))
                    .collect(Collectors.toList());

            for (DailyCovid19CasesDataModel dp : usList)
            {
                try {
                    Date dt = new SimpleDateFormat("dd-MMM-yy").parse(dp.getDate()) ;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dt);
                    Day day = new Day(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+ 1, calendar.get(Calendar.YEAR));

                    Number cases = Double.valueOf(dp.getCases());
                    s1.add(day, cases);
                    dataset.addSeries(s1);
                } catch (Exception e) {
                    System.err.println("Error adding to series");
                }
            }
            for (DailyCovid19CasesDataModel dp : euList)
            {
                try {
                    Date dt = new SimpleDateFormat("dd-MMM-yy").parse(dp.getDate()) ;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dt);
                    Day day = new Day(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+ 1, calendar.get(Calendar.YEAR));

                    Number cases = Double.valueOf(dp.getCases());
                    s2.add(day, cases);
                    dataset.addSeries(s2);
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
                "Daily Covid 19 Cases \n United States and European Union",
                "Date",
                "Cases",
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
            //renderer.setBaseShapesVisible(true);
            // renderer.setBaseShapesFilled(true);
            renderer.setDefaultShapesVisible(true);
            renderer.setDefaultShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, Color.GREEN);
            renderer.setSeriesPaint(2, Color.YELLOW);
        }
        LegendItemCollection legend = plot.getLegendItems();
        int count = 0;

        Iterator iterator=legend.iterator();
        int numItems = legend.getItemCount();
        int halfNumItems = numItems / 2;
        while(iterator.hasNext())
        {
            LegendItem item=(LegendItem)iterator.next();
            count++;
            if(count == 1 || count == numItems/2 + 1)
                continue;
            else
               iterator.remove();
        }
        plot.setFixedLegendItems(legend);


        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd-yy"));

        return chart;
    }

    public static void main( final String[ ] args ) {
         final String title = "Daily Covid 19 Cases US and EU";
        final Covid19CasesDailyUS_EU ts = new Covid19CasesDailyUS_EU( title );
        ts.pack( );
     //   RefineryUtilities.positionFrameRandomly( ts );
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
