# todeb-graduation-project

## Used Technologies

- Java Spring Boot V2.7.2
- JPA and PostGreSql
- Java Spring Security
- Json WebToken V0.4
- Junit 4.13.2
- Swagger V3
- Lombok
- ModelMapper V2.4.5
- Mapstruct V1.5.2 Final

## Expected Running Procedure
For all types, Bank = Admin<br /><br />
  There are two possible types of runnig procedure of application. First type is thought as customer applies credit from online and everything goes well for customer. However, when there is an obligation for credit application result, customer goes to bank and type 2 and 3 gets involved. According discontent of Customer, credit limit can be updated.Also, bank can add new credit score seperately to customer and can determine the credit result. Due to importance of data, all previous credit scores, applications,limits are kept in database. Moreover, for all types, if customer wants update his/her information, there is a necessity for going bank and perform desired process with accompaniation of bank.
### First Type
1. Customer or Admin enters informations of customer and credit score is added automatically.<br /><br />
 ---> by following this URI --> "/api/customer/create"<br /><br />
2. Customer applies for credit application and result is decided automatically.<br /><br />
---> by following this URI --> "/api/application/apply/credit-application/by-identity-number/{identityNo}"<br /><br />
3. Customer can view result with identity number.<br /><br />
---> by following this URI --> "/api/application/view/application-result/{identityNo}"

### Second Type
1. Customer or Admin enters informations of customer and credit score is added automatically.<br /><br />
---> by following this URI --> "/api/customer/create"<br /><br />
2. Bank can add new credit score.<br /><br />
---> by following this URI --> "/api/customer/add/credit-score/by-identity-number/{identityNo}"<br /><br />
3. Bank can add new credit application.<br /><br />
---> by following this URI --> "/api/application/add/credit-application/by-identity-number/{identityNo}"<br /><br />
4. Bank determines the result of application.<br /><br />
---> by following this URI --> "/api/application/determine/result/by-identity-number/{identityNo}"<br /><br />
5. Customer can view result with identity number after credit application is created.<br /><br />
---> by following this URI --> "/api/application/view/application-result/{identityNo}"


## Requirements

1.	User-based (Role-based authorization)
2.	Admin (Bank) / User ( Customer)
3.	Credit application
4.  Requesting credit score of customer.
5.	Determining result of the application and deciding the credit limit according to given standards.
6.	Viewing credit application result.
7.	Login
8.	Sms notification

## Analysis

1.	Bank adds,deletes and updates customers.
2.	Bank finalizes the application
3.	Bank decides the credit limit
4.	Bank sends sms for notification
5.  Customer can become member of bank by writing its informations.
6.	Customer applies for credit
7.	Customer gets Sms notification
8.	Customer views credit result 

## Design

![Use case diagram Todeb Graduation Project](https://user-images.githubusercontent.com/84630121/184538333-c41ceb99-50a5-4c7b-9539-02a84548ebe8.png)










