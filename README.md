# Student Peer Tutoring Portal

A web application that connects students with peer tutors to facilitate collaborative learning and academic support.

## 🚀 Getting Started

### Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

### Setup Instructions

1. **Clone the repository**

   ```bash
   git clone https://github.com/makoto0825/Student_Peer_Tutoring_Portal.git
   cd Student_Peer_Tutoring_Portal
   ```

2. **Database Setup**

   - Install MySQL and start the MySQL service
   - Create a database named `project` (or the application will create it automatically)
   - Update database credentials in `src/main/resources/application.properties` if needed:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/project?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
     spring.datasource.username=root
     spring.datasource.password=your_password_here
     ```

3. **Install Dependencies**

   ```bash
   mvn clean install
   ```

4. **Run the Application**

run the main class `ProjectApplication.java` from your IDE.

5. **Access the Application**
   - Open your browser and navigate to: `http://localhost:8080`
   - The application will be running on port 8080

## 📖 Page Overview

### 🏠 Home Page (`/home`)

**File:** `src/main/resources/templates/home.html`

- **Purpose:** Landing page for all visitors
- **Features:**
  - Welcome message and platform introduction
  - Navigation links to login and registration
  - Feature showcase (Learning Support, Peer Learning, Goal Achievement)
  - Responsive design with gradient background
- **Access:** Public (no authentication required)

### 🔐 Login Page (`/login`)

**File:** `src/main/resources/templates/login.html`

- **Purpose:** User authentication
- **Features:**
  - Username and password input fields
  - Error messages for invalid credentials
  - Success messages from registration
  - Links to home page and registration
- **Access:** Public (redirects to dashboard after successful login)

### 📝 Registration Page (`/register`)

**File:** `src/main/resources/templates/register.html`

- **Purpose:** New user account creation
- **Features:**
  - User information form (username, email, first name, last name, password)
  - Role selection toggle (Student/Tutor)
  - Form validation and error handling
  - Success/error message display
  - Responsive form layout
- **Access:** Public
- **Note:** Role 1 = Student, Role 2 = Tutor

### ✅ Registration Success Page (`/register_success`)

**File:** `src/main/resources/templates/register_success.html`

- **Purpose:** Confirmation page after successful registration
- **Features:**
  - Success confirmation message
  - Direct link to login page
  - Clean, centered design
- **Access:** Public (typically accessed after successful registration)

### 📊 Dashboard Page (`/dashboard`)

**File:** `src/main/resources/templates/dashboard.html`

- **Purpose:** Main user interface after login
- **Features:**
  - Personalized welcome message with username
  - Navigation to different sections
  - Quick access to tutoring sessions
  - Profile settings access
  - Logout functionality
- **Access:** Authenticated users only
- **Security:** Redirects to login if not authenticated

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/example/project/
│   │   ├── ProjectApplication.java          # Main application class
│   │   ├── config/
│   │   │   ├── PasswordEncoderConfig.java   # Password encryption config
│   │   │   └── SecurityConfig.java          # Spring Security configuration
│   │   ├── controller/
│   │   │   ├── HomeController.java          # Home and public pages
│   │   │   └── UserController.java          # User registration/auth
│   │   ├── entity/
│   │   │   └── User.java                    # User entity/model
│   │   ├── repository/
│   │   │   └── UserRepository.java          # Data access layer
│   │   └── service/
│   │       ├── CustomUserDetailsService.java # Spring Security user service
│   │       └── UserService.java             # Business logic for users
│   └── resources/
│       ├── application.properties           # Application configuration
│       ├── static/                          # Static files (CSS, JS, images)
│       └── templates/                       # Thymeleaf HTML templates
└── test/                                    # Test files
```

## 🔧 Technology Stack

- **Backend:** Spring Boot 3.x
- **Security:** Spring Security
- **Database:** MySQL with Spring Data JPA
- **Template Engine:** Thymeleaf
- **Build Tool:** Maven
- **Java Version:** 17+

## 🔑 Key Features

### Authentication & Security

- User registration with role-based access (Student/Tutor)
- Password encryption using BCrypt
- Session management with Spring Security
- CSRF protection enabled

### Database

- MySQL integration with automatic table creation
- User entity with roles and profile information
- JPA repositories for data access

### User Interface

- Responsive design with modern CSS
- Clean, professional styling
- Interactive elements (toggle switches, hover effects)
- Form validation and error handling

## 🚦 Available Endpoints

| Endpoint            | Method   | Description          | Access        |
| ------------------- | -------- | -------------------- | ------------- |
| `/`                 | GET      | Redirects to home    | Public        |
| `/home`             | GET      | Home page            | Public        |
| `/login`            | GET/POST | Login page           | Public        |
| `/register`         | GET/POST | Registration page    | Public        |
| `/register_success` | GET      | Registration success | Public        |
| `/dashboard`        | GET      | User dashboard       | Authenticated |
| `/logout`           | POST     | Logout user          | Authenticated |

## 🔄 Database Schema

### Users Table

| Column     | Type         | Constraints                 | Description            |
| ---------- | ------------ | --------------------------- | ---------------------- |
| id         | BIGINT       | PRIMARY KEY, AUTO_INCREMENT | Unique user identifier |
| username   | VARCHAR(255) | UNIQUE, NOT NULL            | User login name        |
| password   | VARCHAR(255) | NOT NULL                    | Encrypted password     |
| email      | VARCHAR(255) | NOT NULL                    | User email address     |
| first_name | VARCHAR(255) |                             | User's first name      |
| last_name  | VARCHAR(255) |                             | User's last name       |
| role       | INT          | NOT NULL, DEFAULT 1         | 1=Student, 2=Tutor     |
| enabled    | BOOLEAN      | NOT NULL, DEFAULT TRUE      | Account status         |

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**

   - Ensure MySQL is running
   - Check database credentials in `application.properties`
   - Verify database name exists or can be created

2. **Port Already in Use**

   - Change the port in `application.properties`:
     ```properties
     server.port=8081
     ```

3. **Build Failures**
   - Run `mvn clean install` to refresh dependencies
   - Check Java version compatibility

### Logs

- Application logs are displayed in the console
- SQL queries are shown when `spring.jpa.show-sql=true`

## 📝 Development Notes

- The application uses Thymeleaf for server-side rendering
- CSS is embedded in HTML templates for simplicity
- Form validation includes both client-side and server-side checks
- Passwords are automatically encrypted before storing in database

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is for educational purposes as part of Web Application Development coursework.
