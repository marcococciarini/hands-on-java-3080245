package bank;

import java.util.Scanner;
import javax.security.auth.login.LoginException;
import bank.exceptions.*;

public class Menu {

  private Scanner scanner;

  public static void Main(String[] args){
    System.out.println("Welcome to Globe Bank Int.");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if (customer != null){
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }




    // chiudo lo scanner di input
    menu.scanner.close();
  }

  private Customer authenticateUser(){
    System.out.println("Please enter your username");
    String username = scanner.next();

    System.out.println("Please enter your password");
    String password = scanner.next();

    Customer customer = null;
    try{
      customer = Authenticator.login(username, password);
    } catch (LoginException exc){
      System.out.println("LOGIN ERROR : " + exc.getMessage());
    }

    return customer;
  }

  private void showMenu(Customer customer, Account account){
    int menuSelection = 0;

    while(menuSelection != 4 && customer.isAuthenticated()){
      System.out.println("=================================================");
      System.out.println("Please select one of the following options : ");
      System.out.println("1 : Deposit");
      System.out.println("2 : Withdraw");
      System.out.println("3 : Check Balance");
      System.out.println("4 : EXIT");
      System.out.println("=================================================");

      menuSelection = scanner.nextInt();
      double amount = 0;

      switch (menuSelection){
        case 1:
          System.out.println("How much would you like to deposit?");
          amount = scanner.nextDouble();
          try{
          account.deposit(amount);
          } catch (AmountException e){
            System.out.println(e.getMessage());
            System.out.println("Please try again!");
          }
          break;

        case 2:
          System.out.println("How much would you like to withdraw?");
          amount = scanner.nextDouble();
          account.withdraw(amount);
          break;

        case 3:
          System.out.println("Current Balance is : " + account.getBalance());
          break;

        case 4:
          Authenticator.logout(customer);
          System.out.println("User " + customer.getUsername() + " successfully logged out");
          break;

        default:
          System.out.println("Select a valid option between 1 and 4");
          break;
  
      }
    }
  }
}
