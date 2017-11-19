package gui.Test;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Test extends Application{



    public Parent createContent() throws IOException {
        JFXTabPane Tabpane= new JFXTabPane();
        Tabpane.setPrefSize(400, 360);
        Tabpane.setSide(Side.LEFT);
        Tab tab1 = new Tab();
        Tab tab2 = new Tab();
        Tab tab3 = new Tab();
        Tabpane.getTabs().add(tab1);
        final InputStream png = getClass().getResourceAsStream("icon.png");
        final Image image = new Image(png);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(44);
        imageView.setFitWidth(44);
        tab1.setContent( FXMLLoader.load(this.getClass().getResource("StatisticLayout.fxml")));
        tab1.setGraphic(imageView);
        tab2.setGraphic(imageView);
        tab3.setGraphic(imageView);

        Tabpane.getTabs().add(tab3);

        Tabpane.getTabs().add(tab2);

        return Tabpane;
    }
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}
