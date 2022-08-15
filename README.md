# todeb-graduation-project

### Used Technologies

- Java Spring Boot V2.7.2
- JPA and PostGreSql
- Java Spring Security
- Json WebToken V0.4
- Junit 4.13.2
- Swagger V3
- Lombok
- ModelMapper V2.4.5
- Mapstruct V1.5.2 Final

### Expected Running Procedure
There are two possible type of runnig procedure of application
#First Type
1. Customer or Admin enters informations of customer and Customer is created.
2. Customer applies for credit application.
3. Customer can view result after credit application is created
4. Bank adds Credit score to Customer.
5. Bank determines the result of application.

### Requirements

1.	User-based (Role-based authorization)
2.	Admin (Bank) / User ( Customer)
3.	Credit application
4.  Requesting credit score of customer.
5.	Determining result of the application and deciding the credit limit according to given standards.
6.	Viewing credit application result.
7.	Login
8.	Sms notification

### Analysis

1.	Bank adds,deletes and updates customers.
2.	Bank finalizes the application
3.	Bank decides the credit limit
4.	Bank sends sms for notification
5.  Customer can become member of bank by writing its informations.
6.	Customer applies for credit
7.	Customer gets Sms notification
8.	Customer views credit result 

### Design

![Use case diagram Todeb Graduation Project](https://user-images.githubusercontent.com/84630121/184538333-c41ceb99-50a5-4c7b-9539-02a84548ebe8.png)










