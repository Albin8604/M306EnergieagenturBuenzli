package ch.albin.energieagentur;

import ch.albin.energieagentur.assets.Assets;
import ch.albin.energieagentur.util.FXMLHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class Starter extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage primaryStage =  FXMLHelper.load(Assets.Main);
        primaryStage.show();
    }
}
