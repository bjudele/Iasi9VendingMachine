package com.sda.service;

public class PaymentService {

  private final IOService ioService;

  public PaymentService(IOService ioService) {
    this.ioService = ioService;
  }

  public boolean processPayment(int price) {
    ioService.displayPaymentMenu();
    return true;
  }
}
