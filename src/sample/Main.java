package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene mainScene;
    @Override
    public synchronized void start(Stage primaryStage) { //non-stopable processing
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane sp = loader.load();
            sp.setFitToHeight(true);
            sp.setFitToWidth(true);
            mainScene = new Scene(sp);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Sample JavaFX application");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
