
package util;

import java.sql.*;

public class Utility {
    private static Connection con;
    private static Statement stmt;
    public static boolean connectionStatus = false;
       
    public static void getConnection(String sIP){
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
    
}
