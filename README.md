# Contact Management System

A **Spring Boot** web application for managing personal and business contacts, built with **Thymeleaf** for the front-end. The application allows users to register, log in, and manage their contacts with ease. Future enhancements will include **Spring Security** for authentication and authorization.

## Features

- User Registration and Login
- Contact Management (Add, Update, Delete Contacts)
- Server-side validation for input fields
- Flash messages for feedback
- Data persistence using MySQL
- Responsive UI with Bootstrap

## Technologies Used

- **Spring Boot**: Backend framework
- **Thymeleaf**: Server-side rendering engine for HTML
- **MySQL**: Database for storing user and contact information
- **Jakarta Validation**: For server-side form validation
- **Bootstrap 4**: For responsive front-end design

## Project Setup

### Prerequisites

- Java 17 or higher
- MySQL
- Maven

### Installation Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/contact-management.git

2. Navigate to the project directory:
   cd contact-management

3. Update your MySQL credentials in application.properties:
   spring.datasource.url=jdbc:mysql://localhost:3306/contact-manager
   spring.datasource.username=your-username
   spring.datasource.password=your-password

4. Run the application:
   mvn spring-boot:run

5. Access the application at http://localhost:8080.

### Database Setup

- Use the following schema for your MySQL database:
- CREATE DATABASE contact_manager;
- Tables are automatically created by JPA upon application startup.

### Future Enhancements

- Integration of Spring Security for user authentication and role-based authorization.
- Pagination and search features for contact lists.
- Improved UI/UX with additional front-end functionality.

### Contributing
Feel free to contribute by submitting a pull request. For major changes, please open an issue first to discuss the changes.
