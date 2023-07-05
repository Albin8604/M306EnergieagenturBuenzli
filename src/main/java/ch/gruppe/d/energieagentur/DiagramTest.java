package ch.gruppe.d.energieagentur;

import ch.albin.energieagentur.sdatParseTest.SdatParser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

import static javafx.scene.chart.XYChart.*;


public class DiagramTest extends Application {

    @Override
    public void start(Stage stage) {
        SdatParser sdatParser = new SdatParser("src/main/resources/SDAT-Files/20190313_093127_12X-0000001216-O_E66_12X-LIPPUNEREM-T_ESLEVU121963_-279617263.xml");

        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Sequence");
        yAxis.setLabel("Volume");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Sdat visual");
        //defining a series
        Series<Number, Number> series = new Series<>();
        series.setName(sdatParser.getDocumentID());
        //populating the series with data
        System.out.println(sdatParser.getObservation().size());
        for (int i = 1; i < sdatParser.getObservation().size(); i++) {

            series.getData().add(new Data<>(i, sdatParser.getObservation().get(i)));

        }


        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}