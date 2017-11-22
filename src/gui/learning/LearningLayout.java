package gui.learning;

/**
 * Created by GUNGUI on 10/16/2017.
 */

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.loader.user.UserManager;
import com.loader.word.WordManager;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class LearningLayout implements Initializable {
    private int reviewedScore;
    private int newWordScore;
    private static boolean gotRightAnswers;
    private static String currentWord;
    @FXML
    private AnchorPane done;
    @FXML
    private Text answerRate;
    @FXML
    private JFXButton skipButton;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Rectangle bottomBar;
    @FXML
    private AnchorPane bottomPane;
    @FXML
    private JFXButton checkButton;

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton beginButton;

    private void initTab(){
        for(int i=0;i<10;i++) {
             Tab tab = new Tab();
            try {
                int index = 1;
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
        done.setVisible(false);
        tabPane.setTabMaxHeight(0);
        JFXDepthManager.setDepth(bottomBar,1);
        JFXDepthManager.setDepth(tabPane,1);
    }

    public void fadeIn(){
        tabPane.setVisible(true);
        progressBar.setVisible(true);
        bottomPane.setVisible(true);
        FadeTransition ft5 = new FadeTransition(Duration.millis(1000), beginButton);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bottomPane);
        FadeTransition ft2 = new FadeTransition(Duration.millis(1000), checkButton);
        FadeTransition ft3 = new FadeTransition(Duration.millis(1000), skipButton);
        FadeTransition ft4 = new FadeTransition(Duration.millis(1000), progressBar);
        FadeTransition ft6 = new FadeTransition(Duration.millis(1000), tabPane);

        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        ft2.setFromValue(0.0);
        ft2.setToValue(1.0);

        ft3.setFromValue(0.0);
        ft3.setToValue(1.0);

        ft4.setFromValue(0.0);
        ft4.setToValue(1.0);
        ft6.setFromValue(0.0);
        ft6.setToValue(1.0);
        ft5.setFromValue(1.0);
        ft5.setToValue(0.0);
        ft5.play();
        ft.play();
        ft2.play();
        ft3.play();
        ft4.play();
        ft6.play();
        beginButton.setVisible(false);
    }
    private void initButton(){
        nextButton.setVisible(false);
        checkButton.setOnMouseClicked(event -> {
            int currentOpenTab = tabPane.getSelectionModel().getSelectedIndex();
            if(gotRightAnswers){
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
            saveWordData();
        });
        skipButton.setOnMouseClicked(event -> {
            fill("#c5cae9","#d50000");
            skipButton.setVisible(false);
            checkButton.setVisible(false);
            nextButton.setVisible(true);
        });
        nextButton.setOnMouseClicked(event -> {
            int currentOpenTab = tabPane.getSelectionModel().getSelectedIndex();
            if(currentOpenTab == 9){
                saveUserData();
            }
            else
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
    }
    private void fill(String from,String to){
        FillTransition fill = new FillTransition(Duration.seconds(0.3), bottomBar,
                Color.valueOf(from), Color.valueOf(to));
        fill.play();
    }
    public static void setGotAnAnswers(boolean isRightAnswers,String word){
        gotRightAnswers = isRightAnswers;
        currentWord = word;
    }
    private void saveWordData(){
        if(gotRightAnswers){
            WordManager.decresePriority(currentWord);
        }
        else{
          //  WordManager.increasePriority(currentWord);
        }
    }
    private void saveUserData(){
        done.setVisible(true);
        tabPane = new JFXTabPane();
        answerRate.setText(reviewedScore+newWordScore+"/10 câu");
        UserManager.insertNewRecord(reviewedScore,newWordScore);
    }
}