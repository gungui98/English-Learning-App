package com.loader.word;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by GUNGUI on 10/13/2017.
 */
public class WordManager {
    private static final String IMAGE_DIR = "..\\..\\Resource\\WordPicture\\";
    private static WordManager wordManager = new WordManager();
    private static ArrayList<Word> todayWords;
    private static ArrayList<Topic> topics;
    private static DatabaseHelper db;
    private static int index;

    private WordManager() {
        db = new DatabaseHelper();
        todayWords = getTodayWords();
        topics = new ArrayList<>();
        ArrayList<Word> words;
        ArrayList<String> nameOfTopics = db.getAllTopic();

        for (String i : nameOfTopics) {
            words = new ArrayList<>();
            for (String word : db.getWordByTopic(i)) {
                words.add(new Word(word, db.getViMeaning(word), db.getDecription(word)));
            }
            topics.add(new Topic(words, i));
        }

    }

    public static ArrayList<Topic> getTopics() {
        return topics;

    }

    public static void deleteTopic(String topicName) {
        db.deleteTopic(topicName);
        WordManager.reload();
    }
    public static void increasePriority(String word) {
        db.increasePriority(word);
    }

    public static void decresePriority(String word) {
        db.decreasePriority(word);
    }

    public static void deleteWord(String word, String topic) {
        db.deleteWord(word, topic);
        reload();
    }
    public static String getImageDir(String word){
        return IMAGE_DIR+db.getImage(word);
    }
    public static void updateWord(String word, String meaning, String topic, String note) {
        db.updateWord(word, meaning, topic, note);
    }

    public static void loadFile(File file) {
        db.loadRecord(file);
        reload();
    }

    public static void saveFile(File file, ArrayList<Topic> topics) {
        ArrayList<String> nameOfTopics = new ArrayList<>();
        for (Topic topic : topics) {
            nameOfTopics.add(topic.getName());
        }
        db.writeRecord(file, nameOfTopics);
        reload();
    }
    public static Word nextWords(){
        return todayWords.get(index++);
    }
    private static void reload() {
        new WordManager();
    }

    public static ArrayList<Word> getTodayWords() {
        if (todayWords != null) {
            return todayWords;
        } else {
            index =0;
            todayWords = new ArrayList<>();
            int numOfWords = 10;
            if (db.getWordsByPriority(3, numOfWords-1) != null) {
                for (String i : db.getWordsByPriority(3, numOfWords-1)) {
                    todayWords.add(new Word(i, db.getViMeaning(i), db.getDecription(i)));
                }
            }
            if (db.getWordsByPriority(2, numOfWords-2) != null) {
                for (String i : db.getWordsByPriority(2, numOfWords-2)) {
                    todayWords.add(new Word(i, db.getViMeaning(i), db.getDecription(i)));
                }
            }
            if (db.getWordsByPriority(1, numOfWords-3) != null) {
                for (String i : db.getWordsByPriority(1, numOfWords-3)) {
                    todayWords.add(new Word(i, db.getViMeaning(i), db.getDecription(i)));
                }
            }
            if (db.getWordsByPriority(0, numOfWords) != null) {
                for (String i : db.getWordsByPriority(0, numOfWords)) {
                    todayWords.add(new Word(i, db.getViMeaning(i), db.getDecription(i)));
                }
            }
            return todayWords;
        }
    }

    public static ArrayList<String> getWrongAnswer(int i,String except) {
        return db.getRandomWords(i,except);
    }

    public static class Word {

        private String Note;
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

    public class Topic {
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
