package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import sample.Main;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private MenuItem seller;
    @FXML
    private MenuItem department;
    @FXML
    private MenuItem about;

    @FXML
    public void onMenuItemSellerAction() {
        System.out.println("Seller Action");
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        System.out.println("Department Action");
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/util/About.fxml");
    }

    private void loadView(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox vbox = loader.load();
            Scene ms = Main.getMainScene();
            VBox mainVBox = (VBox)((ScrollPane)ms.getRoot()).getContent();
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(vbox.getChildren());
        } catch (IOException e) {
            Alerts.showAlert("IOException", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
