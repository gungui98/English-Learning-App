package com.loader.word;
import com.loader.DataBase;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by GUNGUI on 10/11/2017.
 */
public class DatabaseHelper extends DataBase {

    DatabaseHelper() {
    }

    /**
     * Get all words exist in database
     *
     * @return an array of words
     * @throws SQLException throw exception if any error when face any error in query step
     */

    public ArrayList<String> getAllWords() throws SQLException {
        ArrayList<String> allWords = new ArrayList<>();
        String sql = "SELECT Word FROM Dictionary";
        ResultSet resultSet = super.query(sql);
        while (resultSet.next()){
            allWords.add(resultSet.getString("Word"));
        }
        return allWords;
    }
    public ArrayList<String> getAllTopic() throws SQLException {
        ArrayList<String> topic = new ArrayList<>();
        String sql = "SELECT DISTINCT Topic FROM Dictionary";
        ResultSet resultSet = super.query(sql);
        while(resultSet.next()){
            topic.add(resultSet.getString("Topic"));
        }
        return topic;
    }
    /**
     * Get n elements per slot (priority) from Database
     *
     * @param numOfwords number of words need to get
     * @return an array of words
     */

    public ArrayList<String> getWordsByPriority(int priority,int numOfWords) throws SQLException {
        ArrayList<String> words = new ArrayList<>();
        String sql="SELECT Word FROM Dictionary WHERE Priority ="+priority+" LIMIT "+numOfWords;
        ResultSet resultSet = super.query(sql);
        while(resultSet.next()){
            words.add(resultSet.getString("Word"));
        }
        if(words.size()!=numOfWords){
            return null;
        }
        else return words;
    }

    /**
     * get vietnamese meaning of a word
     *
     * @param word word need to query
     * @return meaning of that word
     * @throws SQLException throw exception if any error when face any error in query step
     */

    public String getViMeaning(String word) throws SQLException {
        String sql = "SELECT Meaning FROM Dictionary WHERE Word = '"+word+"'";
        ResultSet resultSet = super.query(sql);
        return resultSet.getString("Meaning");
    }

    /**
     * get vietnamese meaning of a word
     *
     * @param word word need to query
     * @return meaning of that word
     * @throws SQLException throw exception if any error when face any error in query step
     */

    public String getEnMeaning(String word) throws SQLException {
        String sql = "SELECT Word FROM Dictionary WHRERE Meaning = "+word;
        ResultSet resultSet = super.query(sql);
        return resultSet.getString("Word");
    }
    public String getDecription(String word) throws SQLException {
        String sql = "SELECT Decription FROM Dictionary WHERE Word = '"+word+"'";
        ResultSet resultSet = super.query(sql);
        return resultSet.getString("Decription");

    }

    public String getImage(String word){
        String sql="SELECT Image FROM Dictionary WHERE word ='"+word+"'";
        ResultSet resultSet = super.query(sql);
        try {
            return resultSet.getString("Image");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public void deleteWord(String word,String topic){
        String sql = "DELETE FROM Dictionary WHERE word = '"+word+"' AND Topic = '"+topic+"'";
        super.insert(sql);
    }

    public void deleteTopic(String topic){
        String sql ="DELETE FROM Dictionary WHERE Topic ='"+topic+"'";
        super.insert(sql);
    }
    public ArrayList<String> getWordByTopic(String topic) throws SQLException {
        ArrayList<String> words = new ArrayList<>();
        String sql = "SELECT Word FROM Dictionary WHERE Topic ='"+topic+"'";
        ResultSet resultSet = super.query(sql);
        while (resultSet.next()){
            words.add(resultSet.getString("Word"));
        }
        return words;
    }


    public void updateWord(String word, String meaning, String topic, String note) {
        String sql = "UPDATE Dictionary SET " +
                "Meaning = '"+meaning+"', "+
                "Decription = '"+note+"' "+
                "WHERE topic = '"+topic+"' "+"AND "+
                "Word ='"+word+"'";
        super.insert(sql);
    }
    public void increasePriority(String word){
        String sql = "SELECT Priority FROM Dictionary WHERE word = '"+word+"'";
        ResultSet resultSet = super.query(sql);
        try {
            int priority = resultSet.getInt("Priority");
            if(priority > 0) {
                sql = "UPDATE Dictionary SET Priority = Priority - 1 WHERE word = '" + word + "'";
                super.insert(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void decreasePriority(String word){
        String sql = "SELECT Priority FROM Dictionary WHERE word = '"+word+"'";
        ResultSet resultSet = super.query(sql);
        try {
            int priority = resultSet.getInt("Priority");
            if(priority < 5) {
                sql = "UPDATE Dictionary SET Priority = Priority + 1 WHERE word = '" + word + "'";
                super.insert(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void loadRecord(File file){
        try {
            String sql = "SELECT Id FROM Dictionary";
            ResultSet resultSet = super.query(sql);
            Integer index = 0;
            try {
                while(resultSet.next()){
                    index = resultSet.getInt("Id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Scanner scanner = new Scanner(file);
            //skip title row
            scanner.nextLine();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] data = line.split(",");
                sql = "INSERT INTO Dictionary ( Id, Word, Meaning, Image, Topic, Decription) VALUES (" +
                         +(++index)+ "," +
                        "'" +data[0]+"',"+
                        "'" +data[1]+"',"+
                        "'" +data[2]+"',"+
                        "'" +data[3]+"',"+
                        "'" +data[4]+"'"+
                        ")";
                insert(sql);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeRecord(File file,ArrayList<String> topics) throws SQLException {
        String sql = "SELECT Word, Meaning, Image, Topic, Decription FROM Dictionary";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Word, Meaning, Image, Topic, Decription\n");
        for(String topic:topics) {
            ResultSet resultSet = super.query(sql);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()))) {
                while (resultSet.next()) {
                    if(resultSet.getString("Topic").equals(topic)) {
                        stringBuilder.append(resultSet.getString("Word") + ",");
                        stringBuilder.append(resultSet.getString("Meaning") + ",");
                        stringBuilder.append(resultSet.getString("Image") + ",");
                        stringBuilder.append(resultSet.getString("Topic") + ",");
                        stringBuilder.append(resultSet.getString("Decription") + "\n");
                    }
                }
                writer.write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * unit test
     */
    public static void main(String[] args) throws SQLException {
        DatabaseHelper db= new DatabaseHelper();
        db.decreasePriority("word4");
    }
}