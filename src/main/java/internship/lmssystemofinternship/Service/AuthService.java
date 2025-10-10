package internship.lmssystemofinternship.Service;


import internship.lmssystemofinternship.Dto.AuthRequest;
import internship.lmssystemofinternship.Dto.AuthResponse;
import internship.lmssystemofinternship.Dto.AuthRegisterRequest;
import internship.lmssystemofinternship.Entity.User;
import internship.lmssystemofinternship.Repository.UserRepository;

import internship.lmssystemofinternship.Utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtility  jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 🔹 Register new user
    public AuthResponse register(AuthRegisterRequest request) {
        // Check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return new AuthResponse("Email already registered");
        }

        // Create and save new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("STUDENT"); // default role

        userRepository.save(user);

        // Generate JWT token
        String token = jwtUtility.generateToken(user.getUsername());

        return new AuthResponse("User registered successfully", token);
    }

    // 🔹 Login existing user
    public AuthResponse login(AuthRequest request) {
        // Authenticate user credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtUtility.generateToken(request.getUsername());
            return new AuthResponse("Login successful", token);
        } else {
            return new AuthResponse("Invalid username or password");
        }
    }
}
