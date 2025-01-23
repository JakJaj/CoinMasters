# Coinmasters Project

This project is a comprehensive online budget control system developed as part of a team programing project. It provides a web-based platform writen using React with a REST API created using Spring Boot. The system utilizes various technologies to achieve its functionality. Our version was developed on the AWS using RDS with 2 EC2 instances, one for the api and the second one for frontend. If you want to have it that way you need to do some additional configuration.

## Technologies Used

### Backend

- **Java 17**: The primary programming language used for the backend logic.
- **Project Lombok**: A library to reduce boilerplate code in Java.
- **Spring Boot**: A powerful framework for building Java-based applications.
    - **Spring Web**: Provides HTTP request handling.
    - **Spring Data JPA**: For data access.
    - **Spring Boot Dev Tools**: Tools for rapid application development.
    - **MySQL Driver**: JDBC driver for MySQL database connectivity.
    - **MySQL**: Relational database management system.
    - **Hibernate**: An object-relational mapping framework.

### Frontend

- **HTML/CSS/JS**: Standard web technologies for building user interfaces.
- **React**: A JavaScript library for building user interfaces.
    - **React Router**: Library for managing and navigating the routing of a React application.

## Testing

- **JUnit 5**: A unit testing framework for Java.
- **Mockito**: A mocking framework for unit tests in Java.
- **Postman**: Tool for creating API tests.

## Features

- **User Management**: There is a login/register form in order to create new user or to login as an existing one.
- **Group Management**: This feature provides the ability to create new or join existing groups. For user to cocreate the content of the group.
- **Transactions Management**: Users can manage transactions that are assigned to the group that the user is a part of.


## Getting Started

To run the project locally, follow these steps:

1. Clone the repository: `git clone git@github.com:JakJaj/Coinmasters.git`
2. Navigate to the project backend directory: `cd Coinmasters/backed`
4. Configure the backend:
     If you are having issues with creating api <-> database connection. Checkout the issues on github where you can find the tutorial for the whole process.
     Mainly it is down to the following steps:
     - Set up the MySQL database and update application.properties with the database configuration.
     - Ensure Java 17 is installed and set up on your system.
5. Run the API: `./mvnw spring-boot:run`
6. Navigate to the project frontend directory: `cd Coinmasters/frontend`
7. Run the frontend: `npm run dev`
8. Access the application in your web browser at a web url provided after running `npm run dev`.
