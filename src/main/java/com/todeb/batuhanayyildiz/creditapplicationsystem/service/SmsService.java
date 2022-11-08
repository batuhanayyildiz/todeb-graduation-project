package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {


    public String sendNotificationByCustomer(Customer customer) {
        log.info("Sms business logic is started");
        return "Sms was sent to related customer's phone number: " + customer.getPhoneNo();

    }
}
