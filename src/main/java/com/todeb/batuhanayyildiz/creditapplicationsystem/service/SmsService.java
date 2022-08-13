package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

public interface SmsService {
    String sendNotificationByCustomer(Customer customer);
}
