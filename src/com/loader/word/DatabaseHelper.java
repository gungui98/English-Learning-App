package com.loader.word;
import com.loader.DataBase;

import java.sql.*;
import java.util.ArrayList;
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

    /**
     * Get n first elements from Database
     *
     * @param numOfwords number of words need to get
     * @return an array of words
     */

    public ArrayList<String> getWords(int numOfwords) throws SQLException {
        //TODO reimplement this to use just SQL query
        ArrayList<String> allWords = getAllWords();
        if (numOfwords < 1 || numOfwords > allWords.size()) {
            throw new java.lang.IllegalArgumentException("numbers of word must greater than 0 and less then number of all words in DB");
        }
        ArrayList<String> words = new ArrayList<>();
        for(int i=0;i<numOfwords;i++){
            words.add(allWords.get(i));
        }
        return words;
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

    /**
     * select n random english from database
     *
     * @param num numbers of word
     * @return an array of words
     * @throws SQLException throw exception if any error when face any error in query step
     */
    public ArrayList<String> randomSelectEnWord(int num) throws SQLException {
        ArrayList<String> words = new ArrayList<>();
        String sql = "SELECT word FROM Dictionary WHERE id IN (SELECT id FROM Dictionary ORDER BY RANDOM() LIMIT "+num+")";
        ResultSet resultSet = super.query(sql);
        while(resultSet.next()){
            words.add(resultSet.getString("word"));
        }
        return words;
    }

    /**
     * select n random vietnamese from database
     *
     * @param num numbers of word
     * @return an array of words
     * @throws SQLException throw exception if any error when face any error in query step
     */

    public ArrayList<String> randomSelectViWord(int num) throws SQLException {
        ArrayList<String> words = new ArrayList<>();
        String sql = "SELECT Meaning FROM Dictionary WHERE id IN (SELECT id FROM Dictionary ORDER BY RANDOM() LIMIT "+num+")";
        ResultSet resultSet = super.query(sql);
        while(resultSet.next()){
            words.add(resultSet.getString("Meaning"));
        }
        return words;
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
    public String getDecription(String word) throws SQLException {
        String sql = "SELECT Decription FROM Dictionary WHERE Word = '"+word+"'";
        ResultSet resultSet = super.query(sql);
        return resultSet.getString("Decription");

    }
    /**
     * unit test
     */
    public static void main(String[] args) throws SQLException {
        DatabaseHelper db= new DatabaseHelper();
        db.deleteTopic("Topic1");
        ArrayList<String> test = db.getAllTopic();
        for(String i : test)
            System.out.println((i));
    }

}