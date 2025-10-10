package internship.lmssystemofinternship.Dto;


public class AuthResponse {
    private String message;
    private String token;

    public AuthResponse() {}

    // For responses like “User registered successfully”
    public AuthResponse(String message) {
        this.message = message;
    }

    // For login responses with JWT token
    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
