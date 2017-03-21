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
                } // check out reviews
                else if (selection == 1) {
                    System.out.println("For which restaurant? ");
                    rs = statement.executeQuery("SELECT r.name FROM Restaurants r");

                    // displaying all restaurant choices
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                    }
                    String restaurantChoice;
                    restaurantChoice = scanner.nextLine();

                    // query for selecting the reviews and its rating
                    rs = statement.executeQuery("SELECT Reviews.review, Reviews.rating, Reviews.username "
                            + "FROM Reviews JOIN Restaurants ON Reviews.restaurant_id=Restaurants.restaurant_id"
                            + " where Restaurants.name='" + restaurantChoice + "'");

                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                        System.out.println(rs.getString(2) + " Stars by " + rs.getString(3));
                        System.out.println("");

                    }
                } // leave a review
                else if (selection == 2) {
                    System.out.println("For which restaurant? Select the id");
                    rs = statement.executeQuery("SELECT r.restaurant_id, r.name FROM Restaurants r");

                    // displaying all restaurant names
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + " ");
                        System.out.println(rs.getString(2));
                    }
                    int restaurantChoice;
                    String review;
                    int rating;
                    String name;

                    restaurantChoice = scanner.nextInt();
                    scanner.nextLine();

                    // store review
                    System.out.print("Write your review!: ");
                    review = scanner.nextLine();

                    // store rating
                    System.out.print("What's your rating(1-5)? ");
                    rating = scanner.nextInt();
                    scanner.nextLine();

                    // store username
                    System.out.print("What's your username? ");
                    name = scanner.nextLine();

                    connect.setAutoCommit(false);

                    // run the query to add review in table
                    statement.executeUpdate("INSERT into Reviews (review, rating, restaurant_id, username)"
                            + "values ('" + review + "', " + rating + ", " + restaurantChoice
                            + ", '" + name + "')"
                    );

                    connect.commit();
                } // check out the menu
                else if (selection == 3) {
                    System.out.println("For which restaurant?");
                    rs = statement.executeQuery("SELECT r.name FROM Restaurants r");

                    // displaying all restaurant names
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                    }
                    String restaurantChoice;
                    restaurantChoice = scanner.nextLine();

                    System.out.println("");

                    // execute query to list all menu items
                    rs = statement.executeQuery("SELECT Menu.dishname, Menu.price, Menu.dish_type, Menu.description "
                            + "FROM Menu JOIN Restaurants ON Menu.restaurant_id=Restaurants.restaurant_id"
                            + " where Restaurants.name='" + restaurantChoice + "'");
                    while (rs.next()) {
                        System.out.println("Dishname: " + rs.getString(1));
                        System.out.println("Price: " + rs.getString(2));
                        System.out.println("Type: " + rs.getString(3));
                        System.out.println("Description: " + rs.getString(4));
                        System.out.println("");
                    }
                } // Make an order!
                else if (selection == 4) {
                    System.out.println("For which restaurant? Select the id");
                    rs = statement.executeQuery("SELECT r.restaurant_id, r.name FROM Restaurants r");

                    // displaying all restaurant names
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + " ");
                        System.out.println(rs.getString(2));
                    }
                    int restaurantChoice = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Select the dish: ");
                    rs = statement.executeQuery("Select m.dishname, m.price, m.dish_type"
                            + " from Menu m where restaurant_id="
                            + restaurantChoice + " order by dish_type");

                    while (rs.next()) {
                        System.out.println("Dishname: " + rs.getString(1));
                        System.out.println("Price: " + rs.getString(2));
                        System.out.println("Dish Type: " + rs.getString(3));
                    }
                    // store the dishname
                    String dishname = scanner.nextLine();

                    rs = statement.executeQuery("select m.price from Menu m where dishname='"
                            + dishname + "'");

                    // this stores the price of the selected dish automatically
                    rs.next();
                    double price = Double.parseDouble(rs.getString(1));

                    // store username
                    System.out.println("Whats your username? ");
                    String username = scanner.nextLine();

                    // run the query
                    connect.setAutoCommit(false);

                    statement.executeUpdate("insert into RestaurantTransactions "
                            + "(restaurant_id, dish, price, purchase_date, customer) "
                            + "values(" + restaurantChoice + ", '" + dishname + "', "
                            + price + ", now(), '" + username + "')");

                    connect.commit();

                    System.out.println("Thank you for your order!");
                } // add restaurant
                else if (selection == 5) {
                    String name;
                    String address;
                    String phone;

                    // store name
                    System.out.println("What's the name? ");
                    name = scanner.nextLine();

                    // store address
                    System.out.println("What's the address? ");
                    address = scanner.nextLine();

                    // store phone number
                    System.out.println("What's the phone number? ");
                    phone = scanner.nextLine();

                    connect.setAutoCommit(false);

                    // run the update query
                    statement.executeUpdate("insert into Restaurants(name, address, phone_number) "
                            + "values ('" + name + "', '" + address + "', '" + phone + "')");

                    connect.commit();
                } // add a menu item
                else if (selection == 6) {
                    System.out.println("For which restaurant? Select the id");
                    rs = statement.executeQuery("SELECT r.restaurant_id, r.name FROM Restaurants r");

                    // displaying all restaurant names
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + " ");
                        System.out.println(rs.getString(2));
                    }
                    int restaurantChoice = scanner.nextInt();
                    scanner.nextLine();

                    // store the dish name
                    System.out.println("What's the dish name? ");
                    String dishname = scanner.nextLine();

                    // store the price of dish
                    System.out.println("Price ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    // store dish type
                    System.out.println("What kind of dish is it? ");
                    String dishType = scanner.nextLine();

                    // store the description
                    System.out.println("Short description of the food ");
                    String description = scanner.nextLine();

                    // run the update query
                    connect.setAutoCommit(false);

                    statement.executeUpdate("insert into Menu(dishname, restaurant_id, price, dish_type, description)"
                            + " values ('" + dishname + "', " + restaurantChoice
                            + ", " + price + ", '" + dishType + "', '"
                            + description + "')");

                    connect.commit();
                } // display the listing of the restuarants
                else if (selection == 7) {
                    rs = statement.executeQuery("select r.name, r.address, r.phone_number, r.rating "
                            + "from Restaurants r");

                    while (rs.next()) {
                        System.out.println("Name: " + rs.getString(1));
                        System.out.println("Address: " + rs.getString(2));
                        System.out.println("Phone Number: " + rs.getString(3));
                        System.out.println("Rating: " + rs.getString(4));
                        System.out.println("");
                    }
                } // search username for reviews
                else if (selection == 8) {
                    System.out.println("Enter username");
                    String username = scanner.nextLine();

                    rs = statement.executeQuery("select r.review, r.rating, re.name"
                            + " from Restaurants re join Reviews r on re.restaurant_id=r.restaurant_id"
                            + " where r.username='" + username + "'");

                    while (rs.next()) {
                        System.out.println("Review: " + rs.getString(1));
                        System.out.println("Rating: " + rs.getString(2));
                        System.out.println("Restaurant: " + rs.getString(3));
                        System.out.println("");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printMenu() {
        System.out.println("\nWhat do you want to do?\n");
        System.out.println("(1) - Check out reviews");
        System.out.println("(2) - Add a review");
        System.out.println("(3) - Check out the menu");
        System.out.println("(4) - Make an order");
        System.out.println("(5) - Add a restaurant");
        System.out.println("(6) - Add a menu item");
        System.out.println("(7) - Listing of restaurants");
        System.out.println("(8) - See reviews written by a user");
        System.out.println("(12) - quit");
        System.out.print("Please enter your selection: ");
    }
}
