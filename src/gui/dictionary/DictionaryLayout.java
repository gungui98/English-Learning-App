package gui.dictionary;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.loader.word.WordManager;
import com.loader.word.WordManager.Word;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;


import java.util.ResourceBundle;


public class DictionaryLayout implements Initializable {
    private static String Topic;

    @FXML
    private AnchorPane main;

    @FXML
    private JFXTreeTableView<Word> treeView;

    @FXML
    private JFXTextField searchInput;

    @FXML
    private ImageView close;

    private JFXDepthManager jfxDepthManager;

    private void close(){
        ((Stage) main.getScene().getWindow()).close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        JFXTreeTableColumn<Word, String> deptName = new JFXTreeTableColumn<>("English");
        deptName.setPrefWidth(200);
        deptName.setCellValueFactory(param -> param.getValue().getValue().English);
        treeView.editableProperty().setValue(true);

        JFXTreeTableColumn<Word, String> ageCol = new JFXTreeTableColumn<>("Vietnamese");
        ageCol.setPrefWidth(150);
        ageCol.setCellValueFactory(param -> param.getValue().getValue().Vietnamese);

        JFXTreeTableColumn<Word, String> nameCol = new JFXTreeTableColumn<>("Note");
        nameCol.setPrefWidth(250);
        nameCol.setCellValueFactory(param -> param.getValue().getValue().Note);

        jfxDepthManager.setDepth(treeView,2);

        ObservableList<Word> words = FXCollections.observableArrayList();
/*
        try {
            for(Word i: WordManager.getAllWords())
            words.add(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/


        final TreeItem<Word> root = new RecursiveTreeItem<Word>(words, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(deptName, ageCol, nameCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> treeView.setPredicate(userTreeItem -> {
            Boolean flag = userTreeItem.getValue().English.getValue().contains(newValue);
            Boolean flag2 = userTreeItem.getValue().Note.getValue().contains(newValue);
            return flag||userTreeItem.getValue().Vietnamese.getValue().contains(newValue)||flag2;
        }));

        close.setOnMouseClicked(event -> close());
    }
    public static void setTopic(String topic) {
        Topic = topic;
    }

}

