package com.loader.user;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by GUNGUI on 10/18/2017.
 */
public class UserManager {
    static DataBaseHelper db;
    static ArrayList<UserRecord> records;

    public UserManager() throws SQLException {
        records  = new ArrayList<>();
        db = new DataBaseHelper();
        ArrayList<java.sql.Date> allDate = db.getAllDate();
        ArrayList<Integer> reviewedScore =db.getAllReviewedScore();
        ArrayList<Integer> newWordScore = db.getAllNewWordScore();
        for(int i=0;i<allDate.size();i++){
            UserRecord userRecord = new UserRecord(allDate.get(i),reviewedScore.get(i),newWordScore.get(i));
            records.add(userRecord);
        }
    }
    public static ArrayList<UserRecord> getRecords(){
        return records;
    }
    public static void insertNewRecord(int reviewedScore,int newWordScore){
        db.ImportNewRecord(reviewedScore,newWordScore);
    }

    /**
     * user's record
     */
    public class UserRecord{
        public java.sql.Date date;
        public int Reviewed;
        public int NewWord;

        public UserRecord(java.sql.Date date, int reviewed, int newWord) {
            this.date = date;
            Reviewed = reviewed;
            NewWord = newWord;
        }

        public String getDate() {
            return Integer.toString(date.getDate())+"/"+Integer.toString(date.getMonth());
        }

        public int getReviewed() {
            return Reviewed;
        }

        public int getNewWord() {
            return NewWord;
        }
    }

    /**
     * test
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        UserManager userManager = new UserManager();
        userManager.insertNewRecord(99,99);
        for(UserRecord i:userManager.getRecords()){
            System.out.println(i.getDate()+i.getNewWord()+i.getReviewed());
        }

    }
}
