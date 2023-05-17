# Donation Platform application

This project is a simple version of service for sending money between users. Supported extension is only .jpg.

## Used technologies

* Java 11
* Swagger
* Spring (MVC, Boot, Data Jpa, Security);
* Library Lombok;
* Hibernate
* PostgresSQL
* Maven


## Database
0
Application use PostgreSQL database. For start the application you need Postgres server (jdbc:postgresql://localhost:
5432/donation_platform) with created database 'donation_platform'. Database contains five tables.

* Table _user_table_ - contains information about application users;
* Table _transaction_table_ - contains information about made transactions;
* Table _l_transaction_to_users_ - link table between users and transactions;
* Table _cards_table_ - contains info about cards of users;
* Table _l_cards_of_users_ - link table between cards and users;

## Available endpoints

***

* http://localhost:8080/user/registration - POST method: registration;

* http://localhost:8080/user - POST method for ADMIN: allows to create user without registration;

* http://localhost:8080/user/{id} - GET method: allows to get information about user;

* http://localhost:8080/user/{id} - DELETE method: allows to delete user by his ID, available to everyone but checking
  rights before execution ADMIN can delete all of users, but USER can delete only himself;

* http://localhost:8080/user/putMoney/{sum} - PUT method: allows put money on account by Visa credit card;

* http://localhost:8080/user/all - GET method: allows to get all of users from DataBase;

* http://localhost:8080/user/allCardsOfUser/{id} - GET method: allows to get all of user cards;

* http://localhost:8080/user/allTransactionForUser/{id} - GET method: allows to get all of users transactions, available
  to everyone by ROLE, but checks users rights before show transactions info;

* http://localhost:8080/user/allTransactionForAdmin/{id} - - GET method: allows to get all of users transactions,
  available
  to users which has role ADMIN, but shows detailed information about transactions

* http://localhost:8080/user/update - PUT method: allows update user data in database, but have restrictions on changes:
  available to user with role USER and allows update data:
  - Email;
  - NickName;
  - Login; 
  - Birthdate;
  - Password.

* http://localhost:8080/user/updateByAdmin - PUT method: allows update user data in database, but have restrictions on
  changes:
  available to user with role ADMIN and allows update data:
  - Email;
  - NickName;
  - Login;
  - Password;
  - Rating of user;
  - Role of user.

***

* http://localhost:8080/transaction/{id} - GET method: allows to get data of transaction by id, before doing to check rights of user;
* http://localhost:8080/transaction - POST method: allows to create transaction between two users;

***

* http://localhost:8080/cards - POST method: allows to create card in DataBase;
* http://localhost:8080/cards - DELETE method: allows to delete card from DataBase;
* http://localhost:8080/cards - GET method: allows to get card from DataBase;


## Classes and their purpose
 Dao classes