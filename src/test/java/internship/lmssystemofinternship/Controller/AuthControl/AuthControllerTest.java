package internship.lmssystemofinternship.Controller.AuthControl;

import internship.lmssystemofinternship.Controller.AuthControl.AuthController.*;
import internship.lmssystemofinternship.Repository.UserRepository;
import internship.lmssystemofinternship.Security.CustomUserDetailsService;
import internship.lmssystemofinternship.Utility.JwtUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=" +
    "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration," +
    "org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration," +
    "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration"
})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtility jwtUtility;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldReturn200_whenUserRegistersSuccessfully() throws Exception {
        // Mock repository to return empty for username check
        when(userRepository.findByUsernameIgnoreCase("mastan")).thenReturn(Optional.empty());

        // Mock password encoder
        when(passwordEncoder.encode("123456")).thenReturn("encoded-password");

        // Perform request using MockMvc
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "mastan",
                            "password": "123456"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    void register_shouldReturn400_whenUsernameAlreadyExists() throws Exception {
        // Mock repository to return existing user for username check
        when(userRepository.findByUsernameIgnoreCase("mastan")).thenReturn(Optional.of(new internship.lmssystemofinternship.Entity.User()));

        // Perform request using MockMvc
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "mastan",
                            "password": "123456"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }

    @Test
    void login_shouldReturn200_whenCredentialsAreValid() throws Exception {
        // Mock authentication
        Authentication auth = new UsernamePasswordAuthenticationToken("mastan", "123456");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);

        // Mock JWT utility
        when(jwtUtility.generateToken("mastan")).thenReturn("fake-jwt-token");

        // Perform request using MockMvc
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "mastan",
                            "password": "123456"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    void login_shouldReturn401_whenCredentialsAreInvalid() throws Exception {
        // Mock authentication to throw exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Bad credentials"));

        // Perform request using MockMvc
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "mastan",
                            "password": "wrongpassword"
                        }
                        """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid username or password"));
    }
}
