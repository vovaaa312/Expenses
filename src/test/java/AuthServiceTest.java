
import com.expenses.exception.UserWithEmailExistsException;
import com.expenses.exception.UserWithUsernameExistsException;
import com.expenses.model.request.AuthRequest;
import com.expenses.model.request.RegisterRequest;
import com.expenses.model.response.AuthResponse;
import com.expenses.model.user.SystemRole;
import com.expenses.model.user.User;
import com.expenses.repository.UserRepository;
import com.expenses.service.AuthService;
import com.expenses.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private AuthRequest authRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        authRequest = AuthRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .role(SystemRole.SYSTEM_USER)
                .build();
    }

    @Test
    void register_shouldThrowUserWithUsernameExistsException_whenUsernameExists() {
        // Arrange
        when(userRepository.findUserByUsername(registerRequest.getUsername())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(UserWithUsernameExistsException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_shouldThrowUserWithEmailExistsException_whenEmailExists() {
        // Arrange
        when(userRepository.findUserByUsername(registerRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findUserByEmail(registerRequest.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(UserWithEmailExistsException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }
}