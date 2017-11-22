package gui.learning.Question;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.loader.word.WordManager;
import gui.learning.LearningLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by GUNGUI on 11/19/2017.
 */
public class Type1 implements Initializable {
    private WordManager.Word currentWord;
    private ArrayList<String> wrongAnswer;
    private JFXRadioButton rightButton;
    @FXML
    private Text question;

    @FXML
    private ImageView wordImage;

    @FXML
    private JFXRadioButton answerA;

    @FXML
    private JFXRadioButton answerB;

    @FXML
    private JFXRadioButton answerC;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        answerA.setToggleGroup(toggleGroup);
        answerB.setToggleGroup(toggleGroup);
        answerC.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (toggleGroup.getSelectedToggle() != null) {
                    if(toggleGroup.getSelectedToggle() == rightButton)
                        LearningLayout.setGotAnAnswers(true,currentWord.getEnglish());
                    else
                        LearningLayout.setGotAnAnswers(false,currentWord.getEnglish());
                }
            }
        });

       currentWord = WordManager.nextWords();
       wrongAnswer = WordManager.getWrongAnswer(2,currentWord.getEnglish());

       loadImage();
       initButton((int)(Math.random()*10)%3);
       LearningLayout.setGotAnAnswers(false,currentWord.getEnglish());
    }
    private void initButton(int answer){

        switch (answer){
            case 0:{
                answerA.setText(currentWord.getEnglish());
                answerB.setText(wrongAnswer.get(0));
                answerC.setText(wrongAnswer.get(1));
                rightButton = answerA;
            }break;
            case 1:{
                answerB.setText(currentWord.getEnglish());
                answerC.setText(wrongAnswer.get(0));
                answerA.setText(wrongAnswer.get(1));

                rightButton = answerB;
            }break;
            case 2:{
                answerC.setText(currentWord.getEnglish());
                answerB.setText(wrongAnswer.get(0));
                answerA.setText(wrongAnswer.get(1));
                rightButton = answerC;
            }break;
        }
    }
    private void loadImage(){
        InputStream png = getClass().getResourceAsStream("..\\"+WordManager.getImageDir(currentWord.getEnglish()));
        Image image = new Image(png);
        wordImage.setImage(image);
    }

}
