package gui.learning;

/**
 * Created by GUNGUI on 10/16/2017.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXRippler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LearningLayout implements Initializable {

    @FXML
    private JFXRippler Rippler;
    @FXML
    private JFXProgressBar progressbar;

    @FXML
    private Text question;

    @FXML
    private JFXButton Skip;

    @FXML
    private JFXButton Check;

    @FXML
    private JFXRadioButton buttonA;

    @FXML
    private JFXRadioButton buttonB;

    @FXML
    private JFXRadioButton buttonC;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup group = new ToggleGroup();
        buttonA.setToggleGroup(group);
        buttonB.setToggleGroup(group);
        buttonC.setToggleGroup(group);
        Rippler.setRipplerFill(Color.RED);
        Rippler.setEnabled(true);
    }
}