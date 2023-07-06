package ch.gruppe.d.energieagentur.controller;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.gillius.jfxutils.chart.ChartZoomManager;

public class TestController extends Controller{
    public StackPane chartPane;
    public LineChart chart;
    public Rectangle selectRect;

    @Override
    public void init() {
        ChartZoomManager zoomManager = new ChartZoomManager( chartPane, selectRect, chart );
        zoomManager.start();

    }
}
