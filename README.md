# todeb-graduation-project

## Used Technologies

- Java Spring Boot V2.7.2
- JPA and PostgreSQL
- Java Spring Security
- Json WebToken V0.4
- Junit 4.13.2
- Swagger V3
- Docker
- Lombok  
- Mapstruct V1.5.2 Final
## Desired Requirements by Company
1. Backend:
- By obtaining the identity number, name-surname, monthly income and telephone information from the user, the credit score service, which is assumed to have been written before, is interacted. Then, credit score is obtained from the service. Finally, credit application result is showed to related person.(There may be two options as Approval or Rejection.)
### Rules:
- New users can be defined in the system, existing customers can be updated or deleted.
- If the credit score is below 500, the user will be rejected. (Credit result: Rejected)
- If Credit score between 500 points and 1000 points and monthly income is below 5000 TL, the user's loan application is approved and a limit of 10,000 TL is assigned to the user.(Credit Result: Approval)
- If the credit score is between 500 points and 1000 points and if the monthly income is above 5000 TL,the user's loan application is approved and a limit of 20,000 TL is assigned to the user. (Credit Result: Approval)
- If the credit score is equal to or above 1000 points, The limit is assigned as much as the MONTHLY INCOME * CREDIT LIMIT MULTIPLIER. (Credit Result: Approval)
- As a result of the conclusion of the loan, the relevant application is recorded in the database. Then, If so, a notification SMS will be sent to the relevant phone number.Also, the confirmation status(rejection or approval) and limit information will be sent from the endpoint.  
- A completed loan application can only be queried with an identity number  
Notes: The credit limit multiplier is 4 by default.

## Starting API
By using the commmand below on terminal, API can run on docker container with postgresql database connection <br /><br />
Terminal command:<br /><br />
At first---> "mvn clean install" <br /><br />
Then ---> "docker compose up --build"<br /><br />
After running docker, endpoint's can be reached from below Swagger Url<br /><br />
Swagger URL:<br /><br />
http://localhost:8080/swagger-ui/index.html

## Expected Running Procedure
For roles in application, Bank corresponds to Admin and Customer corresponds to User<br /><br />

1. User or Admin enters informations of customer and credit score is obtained from credit score service.<br /><br />
 ---> by following this URI --> "/v1/customer/create"<br /><br />
2. User applies for credit application and result is decided automatically.<br /><br />
---> by following this URI --> "/v1/credit-application/create/by-identity-number/"<br /><br />
3. User can view result with identity number.<br /><br />
---> by following this URI --> "/v1/credit-application/view-application-result/{identityNo}"<br />

Also,
1. Customer can be deleted with entering customer id by admin.<br /><br />
 ---> by following this URI --> "/v1/customer/delete"<br /><br />
2. Customer can be updated with entering customer id by admin.<br /><br />
---> by following this URI --> "/v1/customer/{id}"<br /><br />

## Design and Analysis
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
7.	Customer gets sms notification
8.	Customer views credit result

### Another Diagram to Show Service Relationship

![image](https://github.com/batuhanayyildiz/todeb-graduation-project/assets/84630121/a88517b9-1396-4dd7-bd5b-683f156d5b00)

### UML Diagram for Defining Actor Relationship

![Use case diagram Todeb Graduation Project](https://user-images.githubusercontent.com/84630121/184538333-c41ceb99-50a5-4c7b-9539-02a84548ebe8.png)










