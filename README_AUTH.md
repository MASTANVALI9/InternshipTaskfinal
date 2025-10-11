# LMS System Authentication

This LMS (Learning Management System) now includes a complete authentication system with login and signup functionality using Spring Boot, OAuth2, JWT, and modern HTML frontend.

## Features

### Authentication System
- **JWT-based Authentication**: Secure token-based authentication
- **OAuth2 Integration**: Spring Security OAuth2 support
- **Role-based Access Control**: ADMIN, TEACHER, STUDENT roles
- **Password Encryption**: BCrypt password hashing

### Frontend Pages
- **Login Page**: Modern, responsive login form with validation
- **Signup Page**: User registration with role selection and password strength indicator
- **Dashboard**: Personalized dashboard with user statistics and quick actions
- **Responsive Design**: Bootstrap 5 with custom CSS for modern UI

### Security Features
- **CSRF Protection**: Disabled for API endpoints
- **Stateless Sessions**: JWT-based session management
- **Protected Routes**: Role-based access control
- **Input Validation**: Client-side and server-side validation

## How to Use

### 1. Start the Application
```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using Maven (if installed)
mvn spring-boot:run
```

### 2. Access the Application
- **Home Page**: http://localhost:8080/ (redirects to login)
- **Login Page**: http://localhost:8080/login
- **Signup Page**: http://localhost:8080/signup
- **Dashboard**: http://localhost:8080/dashboard (requires authentication)

### 3. Authentication Flow

#### Sign Up Process
1. Navigate to `/signup`
2. Fill in username (minimum 3 characters)
3. Enter password (minimum 6 characters with strength indicator)
4. Confirm password
5. Select role (STUDENT, TEACHER, or ADMIN)
6. Click "Create Account"
7. Redirected to login page upon successful registration

#### Login Process
1. Navigate to `/login`
2. Enter username and password
3. Click "Sign In"
4. JWT token stored in localStorage
5. Redirected to dashboard upon successful authentication

#### Dashboard Features
- **User Information**: Display username and role
- **Statistics**: Course count, progress tracking
- **Quick Actions**: Navigation to different sections
- **Logout**: Clear token and redirect to login

### 4. API Endpoints

#### Authentication Endpoints
- `POST /auth/login` - User login
- `POST /auth/register` - User registration

#### User Endpoints
- `GET /api/users/profile` - Get current user profile (requires authentication)

### 5. Database Configuration

The application is configured to use PostgreSQL by default. Make sure you have:
- PostgreSQL running on localhost:5432
- Database named `lmssystem`
- Username: `postgres`, Password: `1234`

For development, you can switch to H2 in-memory database by updating `application.properties`.

## Security Configuration

The security is configured to:
- Allow public access to login/signup pages
- Require authentication for dashboard and API endpoints
- Use JWT tokens for stateless authentication
- Support role-based access control

## Technologies Used

- **Backend**: Spring Boot 3.5.6, Spring Security, Spring Data JPA
- **Authentication**: JWT, OAuth2, BCrypt
- **Frontend**: HTML5, CSS3, JavaScript, Bootstrap 5
- **Database**: PostgreSQL (with H2 fallback)
- **Build Tool**: Maven

## File Structure

```
src/main/
├── java/internship/lmssystemofinternship/
│   ├── Config/
│   │   └── SecurityConfig.java          # Security configuration
│   ├── Controller/
│   │   ├── AuthControl/
│   │   │   └── AuthController.java      # Authentication endpoints
│   │   ├── UserController.java          # User management endpoints
│   │   └── WebController.java           # HTML page controllers
│   ├── Entity/
│   │   └── user.java                     # User entity
│   ├── Security/
│   │   └── CustomUserDetailsService.java # User details service
│   ├── Utility/
│   │   └── JwtUtility.java               # JWT token utilities
│   └── Filter/
│       └── JwtFilter.java                # JWT authentication filter
└── resources/
    ├── templates/
    │   ├── login.html                    # Login page
    │   ├── signup.html                   # Signup page
    │   └── dashboard.html                 # Dashboard page
    └── application.properties            # Application configuration
```

## Testing the Authentication Flow

1. **Start the application**
2. **Create a new account**:
   - Go to http://localhost:8080/signup
   - Fill in the form and select a role
   - Submit the form
3. **Login**:
   - Go to http://localhost:8080/login
   - Enter your credentials
   - You should be redirected to the dashboard
4. **Access protected resources**:
   - The dashboard should display your user information
   - Try accessing `/api/users/profile` with the JWT token

## Troubleshooting

### Common Issues
1. **Database Connection**: Ensure PostgreSQL is running and credentials are correct
2. **Port Conflicts**: Make sure port 8080 is available
3. **JWT Secret**: Update the JWT secret in `application.properties` for production

### Error Messages
- **"Username already exists"**: Try a different username
- **"Login failed"**: Check username/password combination
- **"Network error"**: Check if the application is running

## Next Steps

The authentication system is now complete and ready for use. You can extend it by:
- Adding email verification
- Implementing password reset functionality
- Adding more user profile fields
- Creating role-specific dashboards
- Adding course enrollment features




