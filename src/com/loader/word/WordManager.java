package com.loader.word;

import java.io.File;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

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
                words.add(new Word(word, db.getViMeaning(word), db.getDecription(word)));
            }
            topics.add(new Topic(words,i));
        }
    }
    public static void reload()  {
        try {
            new WordManager();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Topic> getTopics() {
        return topics;

    }

    public static void deleteTopic(String topicName){
        db.deleteTopic(topicName);
        WordManager.reload();
    }
    public static String getImage(String word){
        return db.getImage(word);
    }
    public static void increasePriority(String word){
        db.increasePriority(word);
    }
    public static void decresePriority(String word){
        db.decreasePriority(word);
    }
    public static ArrayList<Word> getWordByNumber(int number){
            return null;
    }
    public static void deleteWord(String word,String topic){
        db.deleteWord(word,topic);
        reload();
    }
    public static void updateWord(String word,String meaning,String topic,String note){db.updateWord(word,meaning,topic,note);}
    public static void loadFile(File file){db.loadRecord(file);
    reload();
    }

    public static void saveFile(File file,ArrayList<Topic> topics) {
        try {
            ArrayList<String> nameOfTopics = new ArrayList<>();
            for(Topic topic:topics){
                nameOfTopics.add(topic.getName());
            }
            db.writeRecord(file,nameOfTopics);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reload();
    }

    public static class  Word  {

        private     String Note;
        private String Vietnamese;
        private String English;

        public Word(String english, String vietnamese, String note) {
            Note = note;
            Vietnamese = vietnamese;
            English = english;
        }

        public String getNote() {
            return Note;
        }

        public String getVietnamese() {
            return Vietnamese;
        }

        public String getEnglish() {
            return English;
        }

        public void setNote(String note) {
            Note = note;
        }

        public void setVietnamese(String vietnamese) {
            Vietnamese = vietnamese;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Word word = (Word) o;

            if (Note != null ? !Note.equals(word.Note) : word.Note != null) return false;
            if (Vietnamese != null ? !Vietnamese.equals(word.Vietnamese) : word.Vietnamese != null) return false;
            return English != null ? English.equals(word.English) : word.English == null;
        }
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Topic topic = (Topic) o;

            return name != null ? name.equals(topic.name) : topic.name == null;
        }

    }
}
