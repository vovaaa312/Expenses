
import com.expenses.configuration.TestApplicationConfig;
import configuration.TestMongoConfig;
import configuration.TestSecurityConfig;
import com.expenses.controller.AuthController;
import com.expenses.model.request.AuthRequest;
import com.expenses.model.request.RegisterRequest;
import com.expenses.model.response.AuthResponse;
import com.expenses.repository.UserRepository;
import com.expenses.service.AuthService;
import com.expenses.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@Import(TestSecurityConfig.class)
@ContextConfiguration(classes = {TestApplicationConfig.class, TestMongoConfig.class})
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Настраиваем поведение JwtService
        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.extractUsername(any())).thenReturn("testuser");

        // Настраиваем поведение UserRepository
        when(userRepository.findUserByUsername(any())).thenReturn(java.util.Optional.empty());
        when(userRepository.findUserByEmail(any())).thenReturn(java.util.Optional.empty());
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Настраиваем поведение PasswordEncoder
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Настраиваем поведение AuthenticationManager
        Authentication authentication = new UsernamePasswordAuthenticationToken("testuser", "password123");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
    }

    @Test
    void register_shouldReturnAuthResponse_whenValidRequest() throws Exception {
        // Arrange
        RegisterRequest request = RegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();
        AuthResponse response = AuthResponse.builder()
                .jwtResponse("jwt-token")
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtResponse").value("jwt-token"));
    }

    @Test
    void authenticate_shouldReturnAuthResponse_whenValidCredentials() throws Exception {
        // Arrange
        AuthRequest request = AuthRequest.builder()
                .username("testuser")
                .password("password123")
                .build();
        AuthResponse response = AuthResponse.builder()
                .jwtResponse("jwt-token")
                .build();

        when(authService.authenticate(any(AuthRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtResponse").value("jwt-token"));
    }
}