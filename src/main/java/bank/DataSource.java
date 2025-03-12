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


  
  public static Account getAccount(int accountId){
    String sql = "select * from Accounts where id = ?";
    Account account = null;

    try(Connection connection = connect();
        PreparedStatement stmt = connection.prepareStatement(sql)){

          stmt.setInt(1, accountId);
          try (ResultSet resultSet = stmt.executeQuery()){
            account = new Account(  resultSet.getInt("id"), 
                                    resultSet.getString("type"),
                                    resultSet.getDouble("balance") );
          }

    } catch (SQLException exc) {
      exc.printStackTrace();
    }

    return account;
  }

  public static void updateAccountBalance(int accountId, double balance){
    String sql = "update Accounts set balance = ? where id = ?";
    
    try (Connection connection = connect();
        PreparedStatement stmt = connection.prepareStatement(sql)) {

      stmt.setDouble(1, balance);
      stmt.setInt(2, accountId);
      
      stmt.executeUpdate();

    } catch (SQLException exc) {
      exc.printStackTrace();
    }
  }

  
  /*
  public static void main(String[] args){
    //connect();

    Customer myCustomer = getCustomer("twest8o@friendfeed.com");
    System.out.println("RETRIEVED CUSTOMER NAME = " + myCustomer.getName());
    
    Account myAccount = getAccount(myCustomer.getAccountId());
    System.out.println("RETRIEVED ACCOUNT BALANCE = " + myAccount.getBalance() );
  }
  */
}
