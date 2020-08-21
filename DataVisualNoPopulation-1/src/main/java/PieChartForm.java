import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PieChartForm extends JFrame {
    private JPanel rootPanel;
    JButton drawPieChartButton;
    JPanel graphPanel;

    public PieChartForm()
    {
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setTitle("Pie Chart");
        this.setVisible(true);
        drawPieChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFreeChart pieChart = ChartFactory.createPieChart("Student Grades",createDataSet());
                ChartPanel chartPanel = new ChartPanel(pieChart);

                graphPanel.removeAll();
                graphPanel.add(chartPanel, BorderLayout.CENTER);
                graphPanel.revalidate();
            }
        });
    }
    private static PieDataset createDataSet() {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("A+", new Double(20));
        dataSet.setValue("B+", new Double(20));
        dataSet.setValue("C+", new Double (40));
        dataSet.setValue("C", new Double (10));
        return dataSet;
    }

    public static void main(String[] args)
    {
        PieChartForm frame = new PieChartForm();
        frame.setVisible(true);
//        final int FRAME_WIDTH = 400;
//        final int FRAME_HEIGHT = 400;
//        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JComponent component = new JComponent()
//        {
//            public void paintComponent(Graphics graph)
//            {
//                draw(graph);
//            }
//        };
//        frame.add(component);
//        frame.setVisible(true);
    }
}