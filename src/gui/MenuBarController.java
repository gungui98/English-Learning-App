package gui;

import com.jfoenix.controls.JFXTabPane;
import com.loader.user.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {

    @FXML
    private AnchorPane background;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab learningTab;

    @FXML
    private Tab statisticTab;

    @FXML
    private Tab dictionaryTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dictionaryTab.setGraphic(loadIcon("dictionary"));
            statisticTab.setGraphic(loadIcon("statistic"));
            learningTab.setGraphic(loadIcon("learning"));
            if(!UserManager.doneToday()) {
                learningTab.setContent(FXMLLoader.load(this.getClass().getResource("learning/LearningLayout.fxml")));
            }
            else {
                learningTab.setContent(FXMLLoader.load(this.getClass().getResource("learning/Done.fxml")));
            }
            statisticTab.setContent( FXMLLoader.load(this.getClass().getResource("statistic/StatisticLayout.fxml")));
            dictionaryTab.setContent( FXMLLoader.load(this.getClass().getResource("dictionary/ListLayout.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageView loadIcon(String option){
        InputStream png = getClass().getResourceAsStream("..\\Resource\\Images\\"+option+".png");
        Image image = new Image(png);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(33);
        imageView.setFitHeight(33);
        imageView.rotateProperty().setValue(90);
        return imageView;
    };
}
