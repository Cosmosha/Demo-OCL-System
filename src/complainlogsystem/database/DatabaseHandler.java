/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem.database;

/**
 *
 * @author user
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

  public class DatabaseHandler {
    
    private static DatabaseHandler handler = null;
    
     public static boolean connectionStatus = false;
    private static Connection con = null;
    private static Statement stmt;
   
  
    
    private DatabaseHandler(){
        createConnection(new util.IPAddressing().getAddress());
        notification();
        setupTable();
       setupDeprtTable();
       complainTable();
    }
    
    public static DatabaseHandler getInstance() {
      if(handler==null)
      {
          handler = new DatabaseHandler();
      }
       return handler; 
    }
       
   private void createConnection(String sIP ){
           try{
            String url = "jdbc:mysql://"+sIP+"/complainlog";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "");
            stmt = con.createStatement();
            connectionStatus = true;
            System.out.println("Connection successful");
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(
                    null,"Database not found!");
        }
    }
    
    public static Statement getStatement(){
        return stmt;
    }
   
    
    
  final void notification(){
      Connection connect = con;
       if (connect.equals(con)){
           NotificationType nt = NotificationType.SUCCESS;
           
           TrayNotification tray = new TrayNotification();
           tray.setTitle("Connection SUCCESSFUL");
           tray.setMessage("Database Server Connection SUCCESSFUL");
           tray.showAndDismiss(Duration.millis(600));
           tray.setNotificationType(nt);
       }
       else {
           NotificationType nt = NotificationType.INFORMATION;
           
           TrayNotification tray = new TrayNotification();
           tray.setTitle("DataBase Server Connection FAILED");
           tray.setMessage("Run Server or Contact System Admin");
           tray.showAndDismiss(Duration.millis(500));
           tray.setNotificationType(nt);
       }
   }
    
  
      
//    public static Statement getStatement(){
//        return stmt;
//    }
    
    
    
  final void setupTable() {
        try {
            String TABLE_NAME = "HOD";
            stmt = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() ,null);
            if(tables.next()){
                System.out.println(""+TABLE_NAME+" already exists. Ready to go..");
            }else{
                String mytable = "Create Table "+TABLE_NAME+"("
                        + "id varchar(20) primary key,\n"
                        + "name varchar(200),\n"
                        + "department varchar(200),\n"
                        + "designation varchar(100),\n"
                        + "username varchar (100),\n"
                        + "password varchar(100),\n"
                        + "phone varchar (10)"
                        + ")";
                stmt.execute(mytable);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
  
  
  final void complainTable() {
        try {
            String TABLE_NAME = "COMPLAIN";
            stmt = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() ,null);
            if(tables.next()){
                System.out.println(""+TABLE_NAME+" already exists. Ready to go..");
            }else{
                String mytable = "Create Table "+TABLE_NAME+"("
                        + "reportedBy varchar(100),\n"
                        + "rptbyDepartment varchar(100),\n"
                        + "problem varchar(100),\n"
                        + "comment varchar(100),\n"
                        + "status varchar (100),\n"
                        + "personnel varchar(100),\n"
                        + "pDepartment varchar(100),\n"
                        + "Txtmessage varchar(500),\n"
                        + "dateTime timestamp default CURRENT_TIMESTAMP"
                        + ")";
                stmt.execute(mytable);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
  
  final void setupDeprtTable() {
        try {
            String TABLE_NAME = "Department";
            stmt = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase() ,null);
            if(tables.next()){
                System.out.println(""+TABLE_NAME+" already exists. Ready to go..");
            }else{
                String mytable = "Create Table "+TABLE_NAME+"("
                        + "id varchar(20) primary key,\n"
                        + "deprt_Name varchar(200),\n"
                        + "location varchar (100)"
                        + ")";
                stmt.execute(mytable);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
  
  
  public ResultSet execQuery(String query){
      ResultSet result;
      
        try {
            stmt= con.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
           System.out.println("Exception at execQuery: dataHandler "+ex.getLocalizedMessage());
           return null;
        }finally{           
        }
          return result;  
   }
  
  
  public boolean execAction(String qu){
      try{
          stmt =con.createStatement();
          stmt.executeUpdate(qu);
          return true;
          
      } catch (SQLException ex) {
            ex = ex.getNextException();
        JOptionPane.showMessageDialog(null, "Error: " +ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
          System.out.println("Exception at execQuery: dataHandler "+ex.getLocalizedMessage());
          return false; 
        }finally{
          
      }
      
  }
  
  
  
    
}
    

