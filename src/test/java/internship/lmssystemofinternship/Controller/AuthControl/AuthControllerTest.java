package internship.lmssystemofinternship.Controller.AuthControl;

import internship.lmssystemofinternship.Service.AuthService;
import internship.lmssystemofinternship.Dto.AuthRequest;
import internship.lmssystemofinternship.Dto.AuthResponse;
import internship.lmssystemofinternship.Dto.AuthRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@Controller
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock the service since we’re not testing it here

    private AuthService authService;

    @Test
    void register_shouldReturn201_whenUserRegistersSuccessfully() throws Exception {
        AuthRegisterRequest request = new AuthRegisterRequest("mastan", "mastan@gmail.com", "123456");
        AuthResponse response = new AuthResponse("User registered successfully");

        // Mock service layer behavior
        when(authService.register(request)).thenReturn(response);

        // Perform request using MockMvc
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "mastan",
                            "email": "mastan@gmail.com",
                            "password": "123456"
                        }
                        """))
                .andExpect(status().isOk()) // or isCreated() if you use 201
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() throws Exception {
        AuthRequest request = new AuthRequest("mastan", "123456");
        AuthResponse response = new AuthResponse("fake-jwt-token");

        when(authService.login(request)).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
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
}
