package com.sda.model;

import com.sda.service.DbService;
import com.sda.service.IOService;
import com.sda.service.PaymentService;
import java.util.Map;
import java.util.stream.Collectors;

public class VendingMachine {

  private transient IOService ioService;
  private transient PaymentService paymentService;
  private transient DbService dbService;

  private Map<Product, Integer> productStock;
  private Map<Coin, Integer> coinStock;
  private String currency;
  private transient final int ADMIN_KEY = 1983;

  public VendingMachine() {
    this.ioService = new IOService();
    this.paymentService = new PaymentService(ioService);
  }

  public void start() {
    while (true) {
      displayMenu();
      int userInput = getUserInput();
      if (isStopCode(userInput)) {
        return;
      }
      processOrder(userInput);
    }
  }

  private boolean isStopCode(int userInput) {
    return userInput == ADMIN_KEY;
  }

  private void processOrder(int userInput) {
    Product product = getProductByCode(userInput);
    boolean paymentSuccessful = paymentService.processPayment(product.getPrice());
    if (paymentSuccessful) {
      releaseProduct(product);
      saveStockToDb();
    }
  }

  private void saveStockToDb() {
    dbService.save(this);
  }

  private void releaseProduct(Product product) {
    ioService.enjoyMessage(product.getName());
    int previousNumberOfProducts = productStock.get(product);
    productStock.put(product, previousNumberOfProducts - 1);
    //  sau, acelasi lucru:
    //  productStock.put(product, productStock.get(product) - 1);
  }

  private Product getProductByCode(final int productCode) {
    return productStock.keySet()
        .stream()
        .filter(product -> product.getCode() == productCode)
        .collect(Collectors.toList())
        .get(0);
  }

  private int getUserInput() {
    while (true) {
      String userInput = ioService.getUserInput();
      if (isValid(userInput)) {
        return Integer.valueOf(userInput);
      }
      //stim sigur ca inputul nu e valid
      ioService.displayInvalidOptionMessage(userInput);
    }
  }

  private boolean isValid(String userInput) {
    if (!userInput.matches("^[0-9]{0,4}$")) {
      return false;
    }
    int userInputAsInt = Integer.parseInt(userInput);

//  varianta 1: cu Stream-uri si 'sau'-ul, totul intr-un singur pas
    return isStopCode(userInputAsInt) || productStock.entrySet()
        .stream()
        .filter(entry -> entry.getKey().getCode() == userInputAsInt
            && entry.getValue() > 0)
        .count() > 0;

//    varianta 2, facuta in clasa, 'old school'
//      if (isStopCode(userInputAsInt)) {
//        return true;
//      }
//    for (Map.Entry<Product, Integer> productEntry : productStock.entrySet()) {
//      if (userInputAsInt == productEntry.getKey().getCode()) {
//        if (productEntry.getValue() > 0) {
//          return true;
//        }
//      }
//    }
//    return false;
  }

  private void displayMenu() {
    ioService.setCurrency(currency);
    ioService.displayHeader();
    for (Map.Entry<Product, Integer> entry : productStock.entrySet()) {
      if (entry.getValue() > 0) {
        //stim sigur ca e pe stock
        ioService.displayProduct(entry.getKey());
      }
    }
  }

  public void setDbService(DbService dbService) {
    this.dbService = dbService;
  }


  @Override
  public String toString() {
    return "VendingMachine{" +
        "currency='" + currency + '\'' +
        ", productStock=" + productStock +
        ", coinStock=" + coinStock +
        '}';
  }
}
