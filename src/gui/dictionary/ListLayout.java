package gui.dictionary;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.effects.JFXDepthManager;
import com.loader.word.WordManager;
import com.loader.word.WordManager.Topic;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static javafx.animation.Interpolator.EASE_BOTH;

public class ListLayout extends Application{
    private ArrayList<Topic> currentTopic;
    private ArrayList<Topic> selectedTopic;
    private ArrayList<Node> selectedChild;
    @FXML
    private  AnchorPane root;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton loadButtun;

    @FXML
    private JFXButton exportButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton mergeButton;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXMasonryPane masonryPane;

    private String getDefaultColor(int i) {
        String color = "#FFFFFF";
        switch (i) {
            case 0:
                color = "#8F3F7E";
                break;
            case 1:
                color = "#B5305F";
                break;
            case 2:
                color = "#CE584A";
                break;
            case 3:
                color = "#DB8D5C";
                break;
            case 4:
                color = "#DA854E";
                break;
            case 5:
                color = "#E9AB44";
                break;
            case 6:
                color = "#FEE435";
                break;
            case 7:
                color = "#99C286";
                break;
            case 8:
                color = "#01A05E";
                break;
            case 9:
                color = "#4A8895";
                break;
            case 10:
                color = "#16669B";
                break;
            case 11:
                color = "#2F65A5";
                break;
            case 12:
                color = "#4E6A9C";
                break;
            default:
                break;
        }
        return color;
    }
    private StackPane buildPane(WordManager.Topic topic){
        StackPane child = new StackPane();
        double width =  150;
        child.setMinWidth(width);
        child.setMaxWidth(width);
        child.setPrefWidth(width);
        double height =  150;
        child.setMinHeight(height);
        child.setMaxHeight(height);
        child.setPrefHeight(height);
        JFXDepthManager.setDepth(child, 1);

        // create content
        StackPane header = new StackPane();
        //title
        Text text = new Text(topic.getName());
        text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, 14));
        text.setFill(Color.WHITE);
        header.getChildren().add(text);
        //modify header color
        String headerColor = getDefaultColor((int) (Math.random()*100 % 12));
        header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + headerColor);
        VBox.setVgrow(header, Priority.ALWAYS);
        StackPane body = new StackPane();
        body.setMinHeight( 50);
        VBox content = new VBox();
        content.getChildren().addAll(header, body);
        body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


        // create button
        JFXButton button = new JFXButton();
        button.setButtonType(ButtonType.RAISED);
        button.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
        button.setPrefSize(40, 40);
        button.setRipplerFill(Color.valueOf(headerColor));
        button.setScaleX(0);
        button.setScaleY(0);
        button.setGraphic(loadImage("edit",20));
        button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
            return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
        }, header.boundsInParentProperty(), button.heightProperty()));
        button.setOnMouseClicked(event -> {
            loadEditor(topic);
        });


        StackPane.setMargin(button, new Insets(0, 12, 0, 0));
        StackPane.setAlignment(button, Pos.TOP_RIGHT);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
                new KeyValue(button.scaleXProperty(),
                        1,
                        EASE_BOTH),
                new KeyValue(button.scaleYProperty(),
                        1,
                        EASE_BOTH)));
        animation.setDelay(Duration.millis( 1100));
        animation.play();
        child.getChildren().addAll(content, button);


        //checkbox
        JFXCheckBox checkBox = new JFXCheckBox();
        JFXDepthManager.setDepth(checkBox,1);
        checkBox.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
            if(isSelected){

                deleteButton.setDisable(false);
                exportButton.setDisable(false);

                selectedTopic.add(topic);
                selectedChild.add(child);
                if(selectedTopic.size()==2)
                    mergeButton.setDisable(false);
            }
            else {
                selectedTopic.remove(topic);
                if(selectedTopic.size()<2){
                    mergeButton.setDisable(true);
                }
                if(selectedTopic.isEmpty()) {
                    exportButton.setDisable(true);
                    deleteButton.setDisable(true);
                }

            }
        });
        checkBox.setTranslateX(4);
        checkBox.setTranslateY(4);
        checkBox.setUnCheckedColor(Color.WHITE);
        child.setAlignment(Pos.TOP_LEFT);
        child.getChildren().add(checkBox);
        return child;
    }
    private void loadTopic(){
        for (Topic i:WordManager.getTopics()) {
            if(!currentTopic.contains(i)) {
                masonryPane.getChildren().add(buildPane(i));
                currentTopic.add(i);
            }
        }
        stackPane.requestLayout();
    }
    private void deleteTopic(){
        int count=0;
        for(Topic i:selectedTopic){
            WordManager.deleteTopic(i.getName());
            masonryPane.getChildren().remove(selectedChild.get(count++));
            Platform.runLater(() -> scrollPane.requestLayout());
            currentTopic.remove(i);
        }
        selectedChild = new ArrayList<>();
        selectedTopic = new ArrayList<>();
        initButton();
    }

    private ImageView loadImage(String option,int size){
        InputStream png = getClass().getResourceAsStream("..\\..\\Resource\\Images\\"+option+".png");
        Image image = new Image(png);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        return imageView;
    }
    private void initButton(){

        loadButtun.setGraphic(loadImage("import",25));
        loadButtun.setOnMouseClicked(event -> loadFile());

        mergeButton.setGraphic(loadImage("merge",25));

        exportButton.setGraphic(loadImage("export",25));
        exportButton.setDisable(true);
        exportButton.setOnMouseClicked(event -> saveFile());

        deleteButton.setGraphic(loadImage("delete",25));
        deleteButton.setOnMouseClicked((event -> confirmDialog("delete")));
        deleteButton.setDisable(true);
    }
    private void confirmDialog(String option){
        stackPane.setVisible(true);
        JFXDialogLayout content = new JFXDialogLayout();

        Text title = new Text("CHÚ Ý");
        Text body = new Text("Hành động này không thể hoàn tác,bạn có muốn tiếp tục không?");
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        body.setFont(Font.font("System", FontWeight.BOLD, 14));


        content.setHeading(title);
        content.setBody(body);
        JFXDialog dialog = new JFXDialog(stackPane,content,JFXDialog.DialogTransition.CENTER);
        JFXButton confirm = new JFXButton("Xác Nhận");
        confirm.setStyle("-fx-font-weight: bold");

        confirm.setOnMouseClicked(event -> {
            if(option.equals("delete"))
                deleteTopic();
            dialog.close();
        });
        dialog.setOnDialogClosed(event -> {
            stackPane.setVisible(false);
        });
        content.setActions(confirm);
        dialog.show();
    }
    private void loadEditor(WordManager.Topic currentTopic) {
        DictionaryLayout.setTopic(currentTopic);
       Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("DictionaryLayout.fxml"));
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void loadFile(){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)","*.csv");
        chooser.getExtensionFilters().add(extFilter);
        File file =chooser.showOpenDialog(masonryPane.getScene().getWindow());
        if(file!=null)
           WordManager.loadFile(file);
        loadTopic();
    }
    private void saveFile(){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)","*.csv");
        chooser.getExtensionFilters().add(extFilter);
        File file =chooser.showSaveDialog(masonryPane.getScene().getWindow());
        if(file!=null)
            WordManager.saveFile(file,selectedTopic);
    }
    public void initialize() {
        currentTopic = new ArrayList<>();
        stackPane.setVisible(false);
        initButton();
        loadTopic();
        selectedChild = new ArrayList<>();
        selectedTopic = new ArrayList<>();
        Platform.runLater(() -> scrollPane.requestLayout());
        JFXScrollPane.smoothScrolling(scrollPane);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize();
    }
}
