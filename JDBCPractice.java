/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.practice;

import java.sql.*;

/**
 *
 * @author Tristan
 */
public class JDBCPractice {
    static Connection connect;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://cslvm74.csc.calpoly.edu:3306/tihonda?user=tihonda&password=8907531");
            ResultSet rs;
            Statement statement = connect.createStatement();
            
            rs = statement.executeQuery("SELECT * FROM CardTypes");
            while(rs.next()) {
                String cardName = rs.getString(1);
                System.out.println("Card name = " + cardName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
}
