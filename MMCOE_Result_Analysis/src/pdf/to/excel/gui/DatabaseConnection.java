/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Durvesh
 */
public class DatabaseConnection {
    protected void connectDB(StringBuffer csvData,JTextField fileName){
         final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
         final String DB_URL = "jdbc:mysql://localhost/abcd";

   //  Database credentials
   final String USER = "root";
   final String PASS = "CaputDraconis@99";
   
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      System.out.println("Database connected successfully...");
     
      System.out.println("Creating table in given database...");
      stmt = conn.createStatement();
     


      boolean tFlag = false;  
      System.out.println(fileName.getText());
      String[] tableName = fileName.getText().split("\\.+");
      String table = tableName[0].toLowerCase().replaceAll("\\s+","");
      System.out.println(table);
      
      ResultSet tExists = conn.getMetaData().getTables(null, null, table, null);
      while(tExists.next()){
          String t = tExists.getString("TABLE_NAME");
          System.out.println(t);
          if(table.equals(t)){
              tFlag = true;
              break;
          }
      }
      
      int i=0,j=0;
      System.out.println(tFlag);
      String s3 = "INSERT INTO " + table + " VALUES(" ;
      String st3 = "CREATE TABLE " + table + "( ";
      for(String[] str = csvData.toString().split("\\n+");i<str.length; i++){
          System.out.println(i);
          j=0;
          String s1 = null, st1 = null;
          for(String[] s = str[i].split(",");j<s.length;s = str[i].split(","),j++){
              if(tFlag == true){}
              else{
              if(i == 0){
                    if(s[j].equals("")){
                        
                      //For sem 1 results to filter out atkt students, remove "SGPA" condition
                        
                      if((s[j+1].equals("") || s[j+1].equals("SGPA")) && s[j-1].equals("")){
                        s[j] = s[j-2] + "G";
                        System.out.println(s[j]);
                      }
                      else if(s[j+1].equals("")){
                        s[j] = s[j-1] + "T";
                        System.out.println(s[j]);
                      }
                      else{
                         s[j] = s[j+1] + "I"; 
                         System.out.println(s[j]);
                      }
                    }
                    else{
                        s[j] = s[j] + "E";
                    }

                    if(j >= 6 && (s[j].equals(s[j-3] + "I") || s[j].equals(s[j-4] + "E") || s[j].equals(s[j-5] + "T") || s[j].equals(s[j-6] + "G"))){
                        s[j] = "1" + s[j];
                        System.out.println(s[j]);
                    }
                    if(j == 0){
                      st1 = s[j] + " TEXT" + ",";
                    }
                    else{
                      if(j < s.length - 1){
                        st1 = st1 + s[j] + " TEXT" + ",";
                      }
                      else{
                        st1 = st1 + s[j] + " TEXT";
                    }
                  }
              }
              else if(i == 1){
                  
              }
              else{
                if(j == 0){
                    s1 = "'" + s[j] + "'" + ",";
                }
                else{
                  if(j < s.length - 1){
                      s1 = s1 + "'" + s[j] + "'" + ",";
                  }
                  else{
                      s1 = s1 + "'" + s[j] + "'";
                  }
                }
              }
          }
          }
          if(tFlag == true){}
          else{
          if(i == 0){
                String st2 = ");";
                String create = String.valueOf(st3 + st1 + st2);
                System.out.println(create);
                stmt.executeUpdate(create);
          }
          else if(i == 1){
              
          }
          else{
            String s2 = ");";
            String insert = String.valueOf(s3 + s1 + s2);
            System.out.println(insert);
            stmt.executeUpdate(insert);
          }
        }
      }      
      System.out.println("Created table in given database...");
      
      int cnt=0,cnt1=0,cnt2=0,cnt3=0,cnt4=0,cnt5=0,cnt6=0,cnt7=0,cnt8=0;
      String n = null,s = null;
      
      //For sem 1 results to filter out atkt students, add and SGPAI = '-' in query except atkt query
      //For sem 1 and 2 results atkt students will be 0
      
      String appeared = "select count(SeatNoE) from "+ table +";";
      ResultSet rs = stmt.executeQuery(appeared);
      while(rs.next()){
        cnt = rs.getInt(1);
        System.out.println(cnt);
      }
      
      String pass = "select count(SeatNoE) from "+ table +" WHERE SGPAE != '--' and StatusE = 'Pass';";
      ResultSet rs1 = stmt.executeQuery(pass);
      while(rs1.next()){
        cnt1 = rs1.getInt(1);
        System.out.println(cnt1);
      }
      
      String fail = "select count(SeatNoE) from "+ table +" WHERE StatusE = 'Fail';";
      ResultSet rs2 = stmt.executeQuery(fail);
      while(rs2.next()){
        cnt2 = rs2.getInt(1);
        System.out.println(cnt2);
      }
      
//      String atkt = "select count(SeatNoE) from "+ table +" WHERE SGPAI != '-';";
//      ResultSet rs9 = stmt.executeQuery(atkt);
//      while(rs9.next()){
//        cnt8 = rs9.getInt(1);
//        System.out.println(cnt8);
//      }

        cnt8 = 0;
      
      String distinction = "select count(SeatNoE) from "+ table +" WHERE SGPAE >= 7.75;";
      ResultSet rs3 = stmt.executeQuery(distinction);
      while(rs3.next()){
        cnt3 = rs3.getInt(1);
        System.out.println(cnt3);
      }
      
      String firstClass = "select count(SeatNoE) from "+ table +" WHERE SGPAE >= 6.75 AND SGPAE < 7.75;";
      ResultSet rs4 = stmt.executeQuery(firstClass);
      while(rs4.next()){
        cnt4 = rs4.getInt(1);
        System.out.println(cnt4);
      }
      
      String HSecondClass = "select count(SeatNoE) from "+ table +" WHERE SGPAE >= 6.25 AND SGPAE < 6.75;";
      ResultSet rs5 = stmt.executeQuery(HSecondClass);
      while(rs5.next()){
        cnt5 = rs5.getInt(1);
        System.out.println(cnt5);
      }
      
      String secondClass = "select count(SeatNoE) from "+ table +" WHERE SGPAE >= 5.5 AND SGPAE < 6.25;";
      ResultSet rs6 = stmt.executeQuery(secondClass);
      while(rs6.next()){
        cnt6 = rs6.getInt(1);
        System.out.println(cnt6);
      }
      
      String passClass = "select count(SeatNoE) from "+ table +" WHERE SGPAE < 5.5 AND StatusE != 'Fail';";
      ResultSet rs7 = stmt.executeQuery(passClass);
      while(rs7.next()){
        cnt7 = rs7.getInt(1);
        System.out.println(cnt7);
      }
      
      String toppers = "select NameE,SGPAE from " + table + " order by SGPAE desc limit 10;";
      ResultSet rs8 = stmt.executeQuery(toppers);
      while(rs8.next()){
         if(n == null){
            n = rs8.getString(1);
         }
         else{
             n = n + "|" + rs8.getString(1);
         }
         if(s == null){
            s = rs8.getString(2);
         }
         else{
             s = s + "|" + rs8.getString(2);
         }
        System.out.println(n);
        System.out.println(s);
      }
      
      String[] name = n.split("\\|+");
      String[] sgpa = s.split("\\|+");
      

      
      ResultScreen rsc = new ResultScreen();
      rsc.resultAnalysis(cnt,cnt1,cnt2,cnt8,cnt3,cnt4,cnt5,cnt6,cnt7,name,sgpa);
      
    }   catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}