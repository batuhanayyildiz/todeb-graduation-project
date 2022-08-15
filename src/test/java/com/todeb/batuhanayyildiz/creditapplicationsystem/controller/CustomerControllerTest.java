package com.todeb.batuhanayyildiz.creditapplicationsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.handler.GenericExceptionHandler;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CreditScoreServiceImpl;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CustomerServiceImpl;
import org.checkerframework.framework.qual.IgnoreInWholeProgramInference;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    private MockMvc mvc;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private CreditScoreServiceImpl creditScoreService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Ignore
    @Test
    void welcome() throws Exception {
        // init
        String expectedWelcomeMessage = "Welcome to Customer Service!";
        // when
        MockHttpServletResponse response = mvc.perform(get("/api/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString().equals(expectedWelcomeMessage));
    }

    @Test
    void getAllCustomers() throws Exception{
        // init test values / given
        List<Customer> expectedCustomers = getSampleTestCustomers();

        // stub - when
        when(customerService.getAllCustomers()).thenReturn(expectedCustomers);

        MockHttpServletResponse response = mvc.perform(get("/api/customer/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Customer> actualCustomers = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Customer>>() {
        });
        assertEquals(expectedCustomers.size(), actualCustomers.size());
    }

    @Test
    void getCustomerById() throws Exception{
        // init test values
        List<Customer> expectedCustomers = getSampleTestCustomers();

        // stub - given
        when(customerService.getCustomerById(1L)).thenReturn(expectedCustomers.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/customer/get/by-id/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer = new ObjectMapper().readValue(response.getContentAsString(), Customer.class);
        Assert.assertEquals(expectedCustomers.get(0).getIdentityNo(), actualCustomer.getIdentityNo());
    }

    @Test
    void getCustomerByIdentityNo() throws Exception {
        // init test values
        List<Customer> expectedCustomers = getSampleTestCustomers();

        // stub - given
        when(customerService.getCustomerByIdentityNo("12345678902")).thenReturn(expectedCustomers.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/customer/get/by-identity-number/12345678902")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer = new ObjectMapper().readValue(response.getContentAsString(), Customer.class);
        Assert.assertEquals(expectedCustomers.get(0).getPhoneNo(), actualCustomer.getPhoneNo());
    }
    @Ignore
    @Test
    void createNewCustomer() throws Exception{
        // init test values

        Customer expectedCustomer=getSampleTestCustomers().get(0);
        CreditScore creditScore=new CreditScore();

        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCustomerJsonStr = ow.writeValueAsString(expectedCustomer);
        Mockito.when(customerService.createCustomer(expectedCustomer)).thenReturn(expectedCustomer);
        Mockito.when(creditScoreService.addCreditScoreToCustomerByCustomer(expectedCustomer)).thenReturn(creditScore);

        MockHttpServletResponse response = mvc.perform(post("/api/customer/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();


        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Mockito.verify(customerService, Mockito.times(1)).createCustomer(any());
    }




    private List<Customer> getSampleTestCustomers() {
        List<Customer> sampleList = new ArrayList<>();
        Customer customer = new Customer(1L,"12345678902","Ali","Genc",5000,
                "05055055050",null,null);
        Customer customer2 = new Customer(2L,"12345678904","Ahmet","Genc",8000,
                "05055055051",null,null);
        Customer customer3 = new Customer(3L,"12345678906","Mehmet","Kaya",10000,
                "05055055052",null,null);
        sampleList.add(customer2);
        sampleList.add(customer);
        sampleList.add(customer3);
        return sampleList;
    }
}