package ch.gruppe.d.energieagentur.component;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.JComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * This class is used to create the line chart
 */
public class LineChartComponent extends JComponent {
    /**
     * Constructor with all the needed data
     * @param produced produced data
     * @param purchased purchased data
     * @param isKwH isKwH boolean
     */
    public LineChartComponent(Map<LocalDateTime, BigDecimal> produced, Map<LocalDateTime, BigDecimal> purchased, boolean isKwH) {
        setLayout(new BorderLayout());

        // Create dataset
        TimeSeriesCollection dataset = createDataset(produced, purchased);

        // Create chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Strombezug und Stromeinspeisung",
                "Datum",
                isKwH ? "kWh":"kW",
                dataset,
                true,
                false,
                false);

        XYPlot plot = (XYPlot) chart.getPlot();

        // config
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShapesVisible(0,false);

        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesShapesVisible(1,false);
        plot.setRenderer(renderer);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        panel.setMaximumSize(new Dimension(1100,450));
        add(panel, BorderLayout.CENTER);
    }

    /**
     * creates datasets for the chart
     * @param produced produced map
     * @param purchased purchased map
     * @return filled set
     */
    private TimeSeriesCollection createDataset(Map<LocalDateTime, BigDecimal> produced, Map<LocalDateTime, BigDecimal> purchased) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        TimeSeries einspeisungNetz = new TimeSeries("Einspeisung Netz");
        for (Map.Entry<LocalDateTime, BigDecimal> entry : produced.entrySet()) {
            einspeisungNetz.addOrUpdate(new Millisecond(java.sql.Timestamp.valueOf(entry.getKey())), entry.getValue());
        }

        TimeSeries bezugNetz = new TimeSeries("Bezug Netz");
        for (Map.Entry<LocalDateTime, BigDecimal> entry : purchased.entrySet()) {
            bezugNetz.addOrUpdate(new Millisecond(java.sql.Timestamp.valueOf(entry.getKey())), entry.getValue());
        }

        dataset.addSeries(einspeisungNetz);
        dataset.addSeries(bezugNetz);

        return dataset;
    }
}
