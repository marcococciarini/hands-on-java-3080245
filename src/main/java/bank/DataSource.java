package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

  public static Connection connect(){
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    try{
      connection = DriverManager.getConnection(db_file);
      System.out.println("Successfully connected to DB: " + db_file);
    } catch (SQLException exc){
      exc.printStackTrace();
    }

    return connection;
  }

  public static Customer getCustomer(String username){
    String sql = "select * from Customers where username = ?";
    Customer customer = null;

    try(Connection connection = connect();
        PreparedStatement stmt = connection.prepareStatement(sql)){

          stmt.setString(1, username);
          try (ResultSet resultSet = stmt.executeQuery()){
            customer = new Customer(
                                    resultSet.getInt("id"), 
                                    resultSet.getString("name"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getInt("account_id") );

              
          }

    } catch (SQLException exc) {
      exc.printStackTrace();
    }

    return customer;
  }

  public static void main(String[] args){
    //connect();

    Customer myCustomer = getCustomer("twest8o@friendfeed.com");
    System.out.println("RETRIEVED CUSTOMER NAME = " + myCustomer.getName());
  }
}
