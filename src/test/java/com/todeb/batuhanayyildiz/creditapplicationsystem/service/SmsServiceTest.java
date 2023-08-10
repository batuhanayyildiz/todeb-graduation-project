package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SmsServiceTest {
    private SmsService smsService;

    @BeforeEach
    public void setUp()
    {

        smsService= new SmsService();

    }


    @Test
    void testSendNotificationByPhoneNumber_shouldReturnStringIncludingPhoneNumber()
    {
        String phoneNo="123";
        String expected= "Sms was sent to related customer's phone number: " + phoneNo;
        String result= smsService.sendNotificationByPhoneNumber(phoneNo);
        assertEquals(result,expected);
    }
}