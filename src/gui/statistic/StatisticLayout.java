package gui.statistic;

import com.jfoenix.effects.JFXDepthManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticLayout  implements Initializable{

    @FXML
    private AnchorPane topBar;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private AnchorPane achivement;
    JFXDepthManager depthManager = new JFXDepthManager();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        depthManager.setDepth(topBar,1);
        depthManager.setDepth(achivement,1);
        depthManager.setDepth(lineChart,1);
    }
}



















/**
 * Created by GUNGUI on 10/16/2017.
 *//*


package gui.statistic;

import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.effects.JFXDepthManager;
import com.loader.user.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


public class StatisticLayout implements Initializable{


    @FXML
    private StackPane pane;

    private JFXDepthManager depthManager;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> date = FXCollections.observableArrayList();
        ObservableList<StackedAreaChart.Data> revieweds = FXCollections.observableArrayList();
        ObservableList<StackedAreaChart.Data> newwords = FXCollections.observableArrayList();
        for(UserManager.UserRecord i :UserManager.getRecords()){
            revieweds.add(new StackedAreaChart.Data(i.getDate(),i.getReviewed()));
            newwords.add(new StackedAreaChart.Data(i.getDate(),i.getNewWord()));
            date.add(i.getDate());
        }
        ObservableList<StackedAreaChart.Series> areaChartData =
                FXCollections.observableArrayList(
                        new StackedAreaChart.Series("Từ Đã Ôn", revieweds),
                        new StackedAreaChart.Series("Từ Mới", newwords)
                );

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(date);
        NumberAxis yAxis = new NumberAxis("Score", 0.0d, 30.0d, 2.0d);

        AreaChart areaChart = new AreaChart(xAxis, yAxis, areaChartData);

        Label chartTitle = new Label("Learning Progress\n\n");
        chartTitle.setStyle("-fx-font-size: 30;-fx-text-alignment: center;-fx-highlight-text-fill: #00b8d4;");
        VBox vBox = new VBox();
        vBox.setMinSize(708,700);
        vBox.getChildren().add(chartTitle);
        vBox.getChildren().add(areaChart);

        ScrollPane scrollPane = new ScrollPane(vBox);
        JFXScrollPane.smoothScrolling(scrollPane);
        depthManager.setDepth(areaChart,1);
        pane.getChildren().add(scrollPane);

    }
}
*/
