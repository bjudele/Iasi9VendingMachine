package com.sda.main;

import com.sda.model.VendingMachine;
import com.sda.service.DbService;

public class Main {

  public static void main(String[] args) {
    DbService dbService = new DbService();
    VendingMachine vendingMachine = dbService.load();
    vendingMachine.setDbService(dbService);
    vendingMachine.start();
  }
}
