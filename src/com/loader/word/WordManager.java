package com.loader.word;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by GUNGUI on 10/13/2017.
 */
public class WordManager {

    private static ArrayList<Topic> topics;
    private static DatabaseHelper db;
    public WordManager() throws SQLException {
        db = new DatabaseHelper();
        topics = new ArrayList<>();
        ArrayList<Word> words;
        ArrayList<String> nameOfTopics = db.getAllTopic();

        for(String i : nameOfTopics){
            words = new ArrayList<>();
            for(String word:db.getWordByTopic(i)){
                words.add(new Word(word,db.getViMeaning(word),db.getDecription(word)));
            }
            topics.add(new Topic(words,i));
        }
    }
    public static void reload() throws SQLException {
        new WordManager();
    }
    public static ArrayList<Topic> getTopics() {
        return topics;
    }

    public static void deleteTopic(String topicName){
        db.deleteTopic(topicName);
    }
    public class Topic{
        private ArrayList<Word> words;
        private String name;

        public Topic(ArrayList<Word> words, String name) {
            this.words = words;
            this.name = name;
        }

        public ArrayList<Word> getWords() {
            return words;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class  Word extends RecursiveTreeObject<Word> {

        public StringProperty Note;
        public StringProperty Vietnamese;
        public StringProperty English;

        public Word(String English, String Vietnamese, String Note) {
            this.English = new SimpleStringProperty(English);
            this.Note = new SimpleStringProperty(Note);
            this.Vietnamese = new SimpleStringProperty(Vietnamese);
        }

    }
}
