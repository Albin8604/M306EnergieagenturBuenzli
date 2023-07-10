package ch.gruppe.d.energieagentur;

import ch.gruppe.d.energieagentur.assets.Assets;
import ch.gruppe.d.energieagentur.util.FXMLHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class Starter extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage primaryStage = FXMLHelper.load(Assets.Main);
        //Stage primaryStage =  FXMLHelper.load(Assets.Test);
        primaryStage.show();
    }
}
