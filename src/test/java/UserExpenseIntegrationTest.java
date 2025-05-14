
import com.expenses.model.expense.Expense;
import com.expenses.model.user.SystemRole;
import com.expenses.model.user.User;
import com.expenses.repository.ExpenseRepository;
import com.expenses.repository.UserRepository;
import com.expenses.service.ExpenseService;
import com.expenses.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = com.expenses.ExpensesApplication.class)
@ActiveProfiles("test")
class UserExpenseIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        expenseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUserAndAddExpense() {
        // Arrange
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .role(SystemRole.SYSTEM_USER)
                .build();
        User savedUser = userService.addUser(user);

        Expense expense = Expense.builder()
                .name("Test Expense")
                .description("Test Description")
                .price(100.0)
                .amount(1)
                .date("2025-05-14")
                .category("Food")
                .userId(savedUser.getId())
                .build();

        // Act
        Expense savedExpense = expenseService.save(expense);
        List<Expense> expenses = expenseService.findAllByUserId(savedUser.getId());

        // Assert
        assertNotNull(savedExpense.getId());
        assertEquals(1, expenses.size());
        assertEquals("Test Expense", expenses.get(0).getName());
        assertEquals(savedUser.getId(), expenses.get(0).getUserId());
    }
}