package com.loader.user;

import com.loader.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by GUNGUI on 10/14/2017.
 */
public class DataBaseHelper extends DataBase{

    DataBaseHelper(){}

    public ArrayList<java.sql.Date> getAllDate() throws SQLException {
        ArrayList<java.sql.Date> dates = new ArrayList<>();
        String sql = "SELECT Date FROM user";
        ResultSet resultSet = super.query(sql);
        while(resultSet.next()){
            dates.add(resultSet.getDate("Date"));
        }
        return dates;
    }
    public ArrayList<Integer> getAllReviewedScore() throws SQLException {
        ArrayList<Integer> reviewedScores = new ArrayList<>();
        String sql = "SELECT Reviewed FROM user";
        ResultSet resultSet = super.query(sql);
        while (resultSet.next()){
            reviewedScores.add(resultSet.getInt("Reviewed"));
        }
        return reviewedScores;
    }
    public ArrayList<Integer> getAllNewWordScore() throws SQLException {
        ArrayList<Integer> newWordScores  = new ArrayList<>();
        String sql = "SELECT NewWord FROM user";
        ResultSet resultSet = super.query(sql);
        while(resultSet.next()){
            newWordScores.add(resultSet.getInt("NewWord"));
        }
        return newWordScores;
    }
    public void ImportNewRecord(int reviewedScore, int newWordScore){
        Long now =Calendar.getInstance().getTimeInMillis();
        String sql = "INSERT INTO user (Date,Reviewed,NewWord) VALUES ("+now+','+reviewedScore+','+newWordScore+")";
        super.insert(sql);
    }
    public static void main(String[] args) throws SQLException {
        DataBaseHelper dataBaseHelper = new DataBaseHelper();

      //  System.out.println(i);

    }
}
