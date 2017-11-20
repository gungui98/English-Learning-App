package gui.learning;

/**
 * Created by GUNGUI on 10/16/2017.
 */

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.loader.user.UserManager;
import com.loader.word.WordManager;
import javafx.animation.FillTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LearningLayout implements Initializable {
    private int reviewedScore;
    private int newWordScore;
    private static boolean gotRightAnswers;
    @FXML
    private JFXButton skipButton;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Rectangle bottomBar;

    @FXML
    private JFXButton checkButton;

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private JFXButton nextButton;

    private void initTab(){

        for(int i=0;i<10;i++) {
            Tab tab = new Tab();
            try {
                int index = 1;//(int) (Math.random()*10%3+1);
                tab.setContent(FXMLLoader.load(this.getClass().getResource("Question/Type" + index + ".fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tabPane.getTabs().add(tab);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reviewedScore = 0;
        newWordScore = 0;
        initButton();
        initTab();
        tabPane.setTabMaxHeight(0);
        JFXDepthManager.setDepth(bottomBar,1);
    }
    private void initButton(){
        nextButton.setVisible(false);
        checkButton.setOnMouseClicked(event -> {
            int currentOpenTab = tabPane.getSelectionModel().getSelectedIndex();
            if(true){
                if(currentOpenTab>=5){
                    reviewedScore++;
                }
                else{
                    newWordScore++;
                }
                fill("#c5cae9","#00c853");

            }
            else{
                fill("#c5cae9","#d50000");
            }
            skipButton.setVisible(false);
            checkButton.setVisible(false);
            nextButton.setVisible(true);
        });
        skipButton.setOnMouseClicked(event -> {
            fill("#c5cae9","#d50000");
            skipButton.setVisible(false);
            checkButton.setVisible(false);
            nextButton.setVisible(true);
        });
        nextButton.setOnMouseClicked(event -> {
            int currentOpenTab = tabPane.getSelectionModel().getSelectedIndex();
            tabPane.getSelectionModel().select(currentOpenTab+1);
            resetLayout();
        });
    }
    private void resetLayout(){
        progressBar.setProgress(progressBar.getProgress()+0.1);
        gotRightAnswers = false;
        fill(bottomBar.getFill().toString(),"#c5cae9");

        skipButton.setVisible(true);
        checkButton.setVisible(true);
        nextButton.setVisible(false);
        System.out.println(reviewedScore+" "+newWordScore);
    }
    private void fill(String from,String to){
        FillTransition fill = new FillTransition(Duration.seconds(0.3), bottomBar,
                Color.valueOf(from), Color.valueOf(to));
        fill.play();
    }
    public static void setGotRightAnswers(boolean RightAnswers,String word){
        gotRightAnswers = RightAnswers;
        if(gotRightAnswers){
            WordManager.decresePriority(word);
        }
        else{
            WordManager.increasePriority(word);
        }
    }
}