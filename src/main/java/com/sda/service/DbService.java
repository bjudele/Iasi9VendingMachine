package com.sda.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sda.model.VendingMachine;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class DbService {

  private static final String STOCK_FILE_PATH = "C:\\Users\\bjudele\\D\\Projects\\SDA\\Iasi9\\VendingMachineIasi9\\src\\main\\resources\\stock.json";

  private Gson gson;

  public DbService() {
    gson = new GsonBuilder().enableComplexMapKeySerialization()
        .setPrettyPrinting().create();
  }


  public VendingMachine load() {
    VendingMachine vendingMachine = null;
    try {
      vendingMachine = gson.fromJson(
          new FileReader(STOCK_FILE_PATH), VendingMachine.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return vendingMachine;
  }

  public void save(VendingMachine vendingMachine) {
    try {
      Writer writer = new OutputStreamWriter(
          new FileOutputStream(STOCK_FILE_PATH), "UTF-8");
      gson.toJson(vendingMachine, writer);
      writer.close();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
    }
  }
}
