package com.sda.service;

import com.sda.model.Product;
import java.util.Map;
import java.util.Scanner;

public class IOService {

  Scanner scanner = new Scanner(System.in);
  private String currency;

  public void displayProduct(Product product) {
    System.out.println(product.getCode() + "\t\t" +
        product.getName() + "\t\t" +
        product.getPrice() + currency + "\t\t" +
        product.getSize());
  }

  public void displayHeader() {
    System.out.println("Code\tProduct\tPrice\tSize");
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getUserInput() {
    return scanner.nextLine();
  }

  public void displayInvalidOptionMessage(String userInput) {
    System.out.println("Option " + userInput + " is not valid!");
  }

  public void displayPrice(int price) {
    System.out.println("Please insert " + price + currency + " amount");
  }

  public void enjoyMessage(String productName) {
    System.out.println("Enjoy your " + productName + "!");
  }

  public void displayPaymentMenu() {
    System.out.println("PAYMENT MENU!!!");
  }
}
