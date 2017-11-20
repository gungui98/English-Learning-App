package gui.statistic;

import com.jfoenix.effects.JFXDepthManager;
import com.loader.user.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticLayout  implements Initializable{


    @FXML
    private AnchorPane topBar;

    @FXML
    private LineChart<String,Number> lineChart;

    @FXML
    private AnchorPane achivement;

    @FXML
    private Text correctAnswer;

    @FXML
    private Text learnedWord;

    JFXDepthManager depthManager = new JFXDepthManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChart();
        depthManager.setDepth(topBar,1);
        depthManager.setDepth(achivement,1);
        depthManager.setDepth(lineChart,1);
    }
    private void initChart(){
        int numberOfRightAnswer = 0;
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("Từ Mới");
        series2.setName("Từ Đã Ôn");
        for(UserManager.UserRecord i : UserManager.getRecords()) {
            numberOfRightAnswer+=(i.getNewWord()+i.getReviewed());
            series1.getData().add(new XYChart.Data(i.getDate(),i.getNewWord()));
            series2.getData().add(new XYChart.Data(i.getDate(),i.getReviewed()));
        }
        Float percentage = (float) numberOfRightAnswer / (UserManager.getRecords().size()*10)*100;
        correctAnswer.setText(String.valueOf(percentage.shortValue())+"%");
        learnedWord.setText(Integer.toString(numberOfRightAnswer));
        lineChart.getData().setAll(series1,series2);
        lineChart.getXAxis().setTickLabelRotation(25);
    }
}