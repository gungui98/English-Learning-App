package gui.dictionary;

import com.jfoenix.controls.*;
import com.loader.word.WordManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import com.loader.word.WordManager.Word;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class DictionaryLayout implements Initializable {
    private static WordManager.Topic Topic;
    private static ArrayList<Label> labels;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane main;

    @FXML
    private JFXListView<Label> wordList;

    @FXML
    private ImageView close;

    @FXML
    private ImageView wordImage;
    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private Text eng;

    @FXML
    private JFXTextField vi;

    @FXML
    private JFXTextField decrip;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stackPane.setVisible(false);
        labels = new ArrayList<>();

        initContent(null,null,null);
       for(Word word:Topic.getWords()) {
           Label label = new Label(word.getEnglish());
           labels.add(label);
           wordList.setOnMouseClicked(event -> {
               int index = wordList.getSelectionModel().getSelectedIndex();
               Word currentSelectedWord = Topic.getWords().get(index);
               initContent(currentSelectedWord.getEnglish(),currentSelectedWord.getVietnamese(),currentSelectedWord.getNote());
           });

       }
       wordList.getItems().addAll(labels);
       close.setOnMouseClicked(event -> close());
    }
    private void deleteWord(){
        Topic.getWords().remove(new Word(eng.getText(), vi.getText(), decrip.getText()));
        wordList.getItems().remove(wordList.getSelectionModel().getSelectedItem());
        wordList.setShowTooltip(true);

        WordManager.deleteWord(eng.getText(),Topic.getName());

            WordManager.reload();

    }
    private void update(){
        Word word = Topic.getWords().get(wordList.getSelectionModel().getSelectedIndex());
        word.setVietnamese(vi.getText());
        word.setNote(decrip.getText());
        WordManager.updateWord(eng.getText(),vi.getText(),Topic.getName(),decrip.getText());
    }
    private void initContent(String english,String vietnamese,String decription){
        //init button
        saveButton.setDisable(true);
        deleteButton.setOnMouseClicked(event -> confirmDialog("delete"));
        saveButton.setOnMouseClicked(event -> update());
        //init Text
        eng.setText(english);
        eng.setTextAlignment(TextAlignment.CENTER);

        vi.setText(vietnamese);
        vi.setOnAction(event -> {
            saveButton.setDisable(false);
        });

        decrip.setText(decription);
        decrip.setOnAction(event -> {
            saveButton.setDisable(false);
        });

    }

    public static void setTopic(WordManager.Topic topic) {
        Topic = topic;
    }

    private void close(){
        ((Stage) main.getScene().getWindow()).close();
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
                deleteWord();
            dialog.close();
        });
        dialog.setOnDialogClosed(event -> {
            stackPane.setVisible(false);
        });
        content.setActions(confirm);
        dialog.show();
    }
}

