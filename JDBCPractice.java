/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.practice;

import java.sql.*;
import java.util.*;
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
            
            Scanner scanner = new Scanner(System.in);
            
            // connecting to the database. for some reason, i need to be on 
            // campus?
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("connecting...");
            
            connect = DriverManager.getConnection("jdbc:mysql://cslvm74.csc.calpoly.edu:3306/tihonda?user=tihonda&password=8907531");
            System.out.println("connected");
            
            Statement statement = connect.createStatement();
            ResultSet rs;
            
            // interface beginning. 
            while (true) {
                printMenu();
                int selection = scanner.nextInt();
                scanner.nextLine();
                System.out.println("");
                
                // quit the application
                if (selection == 12) {
                    break;
                }
                // check out reviews
                else if (selection == 1) {
                    System.out.println("For which restaurant? ");
                    rs = statement.executeQuery("SELECT r.name FROM Restaurants r");
                   
                    // displaying all restaurant names
                    while(rs.next()) {
                        System.out.println(rs.getString(1));
                    }
                    String restaurantChoice; 
                    restaurantChoice = scanner.nextLine();
                    
                    // query for selecting the reviews and its rating
                    rs = statement.executeQuery("SELECT Reviews.review, Reviews.rating "
                        + "FROM Reviews JOIN Restaurants ON Reviews.restaurant_id=Restaurants.restaurant_id"
                        + " where Restaurants.name='" + restaurantChoice + "'");
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                        System.out.println(rs.getString(2) + " Stars");
                        System.out.println("");
                    }
                 }
                // leave a review
                else if (selection == 2) {
                    System.out.println("For which restaurant? Select the id");
                    rs = statement.executeQuery("SELECT r.restaurant_id, r.name FROM Restaurants r");
                   
                    // displaying all restaurant names
                    while(rs.next()) {
                        System.out.print(rs.getString(1) + " ");
                        System.out.println(rs.getString(2));
                    }
                    int restaurantChoice; 
                    String review;
                    int rating;
                    String name;
                    
                    restaurantChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Write your review!: ");
                    review = scanner.nextLine();
                    
                    System.out.print("What's your rating(1-5)? ");
                    rating = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("What's your name? ");
                    name = scanner.nextLine();
                    
                    connect.setAutoCommit(false);
                    
                    statement.executeUpdate("INSERT into Reviews (review, rating, restaurant_id, name)" +
                        "values ('" + review + "', " + rating + ", " + restaurantChoice +
                        ", '" + name + "')"
                    );
                }
                // check out the menu
                else if (selection == 3) {
                    System.out.println("For which restaurant?");
                    rs = statement.executeQuery("SELECT r.name FROM Restaurants r");
                    
                    // displaying all restaurant names
                    while(rs.next()) {
                        System.out.println(rs.getString(1));
                    }
                    String restaurantChoice;
                    restaurantChoice = scanner.nextLine();
                    
                    rs = statement.executeQuery("SELECT Menu.dishname, Menu.price, Menu.dish_type, Menu.description "
                        + "FROM Menu JOIN Restaurants ON Menu.restaurant_id=Restaurants.restaurant_id"
                        + " where Restaurants.name='" + restaurantChoice + "'");
                    while (rs.next()) {
                        System.out.println("Dishname: " + rs.getString(1));
                        System.out.println("Price: " + rs.getString(2));
                        System.out.println("Type" + rs.getString(3));
                        System.out.println("Description: " + rs.getString(4));
                        System.out.println("");
                    }
                }
                // Make an order!
                else if (selection == 4) {
                    
                }
            }
            
            
            
            // sample code for adding a restaurant. 
//            Statement statement = connect.createStatement();
//            connect.setAutoCommit(false);
//            String str = "Restaurants";
//            statement.executeUpdate("Insert into " + str + " (name, address, phone_number) values ('Thai Classic', '2342', '2342342')");
//                    
//            statement.close();
//            connect.commit();
//            connect.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
    }
    public static void printMenu() {
        System.out.println("\nWhat do you want to do?\n");
        System.out.println("(1) - Check out reviews");
        System.out.println("(2) - Add a review");
        System.out.println("(3) - Check out the menu");
        System.out.println("(4) - Make an order");
        System.out.println("(12) - quit");
        System.out.print("Please enter your selection: ");
    }
}

