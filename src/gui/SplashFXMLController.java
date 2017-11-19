package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.effects.JFXDepthManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SplashFXMLController implements Initializable {

    @FXML
    private StackPane rootPane;
    private JFXDepthManager manager;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = new JFXDepthManager();
        manager.setDepth(rootPane,3);
        new SplashSceen().start();
    }
    class SplashSceen extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        Stage primaryStage = new Stage();
                        try {
                            root = FXMLLoader.load(getClass().getResource("/gui/Menubar.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JFXDecorator decorator = new JFXDecorator(primaryStage, root);

                        primaryStage.resizableProperty().setValue(Boolean.FALSE);
                        primaryStage.setResizable(false);
                        primaryStage.setMaximized(false);

                        Scene scene = new Scene(decorator);

                        final ObservableList<String> stylesheets = scene.getStylesheets();
                        stylesheets.addAll(MenuBarController.class.getResource("/gui/css/main.css").toExternalForm());

                        primaryStage.setScene(scene);
                        primaryStage.show();
                        rootPane.getScene().getWindow().hide();
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
