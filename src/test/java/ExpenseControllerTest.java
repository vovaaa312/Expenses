
import com.expenses.configuration.TestApplicationConfig;
import configuration.TestMongoConfig;
import configuration.TestSecurityConfig;
import com.expenses.controller.ExpenseController;
import com.expenses.model.dTo.ExpenseDto;
import com.expenses.model.dTo.mapper.ExpenseMapper;
import com.expenses.model.expense.Expense;
import com.expenses.model.user.User;
import com.expenses.repository.ExpenseRepository;
import com.expenses.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExpenseController.class)
@Import(TestSecurityConfig.class)
@ContextConfiguration(classes = {TestApplicationConfig.class, TestMongoConfig.class})
@AutoConfigureMockMvc(addFilters = false)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @MockBean
    private ExpenseRepository expenseRepository;

    @MockBean
    private ExpenseMapper expenseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private User principal;
    private Expense expense;
    private ExpenseDto expenseDto;

    @BeforeEach
    void setUp() {
        principal = new User();
        principal.setId("user1");
        principal.setUsername("testuser");
        principal.setEmail("test@example.com");
        principal.setPassword("password");

        LocalDateTime dateTime = LocalDateTime.of(2025, 5, 14, 12, 0);
        expense = new Expense();
        expense.setId("expense1");
        expense.setUserId("user1");
        expense.setName("Lunch");
        expense.setDescription("Lunch at cafe");
        expense.setAmount(100.0);
        expense.setDate(String.valueOf(dateTime));
        expense.setCategory("Food");
        expense.setPrice(100.0);

        // Форматируем дату в строку
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dateString = dateTime.format(formatter);

        expenseDto = ExpenseDto.builder()
                .id("expense1")
                .userId("user1")
                .name("Lunch")
                .description("Lunch at cafe")
                .amount(100.0)
                .date(dateString)
                .category("Food")
                .price(100.0)
                .build();

        // Мокаем методы ExpenseMapper
        when(expenseMapper.toDto(any(Expense.class))).thenReturn(expenseDto);
        when(expenseMapper.toEntity(any(ExpenseDto.class))).thenReturn(expense);
        when(expenseMapper.toDtoList(any())).thenReturn(Arrays.asList(expenseDto));
    }

    @Test
    void findById_shouldReturnExpenseDto_whenAuthorized() throws Exception {
        when(expenseService.findById("expense1")).thenReturn(expense);

        mockMvc.perform(get("/api/expenses/findById/expense1")
                        .with(user((UserDetails) principal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("expense1"))
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    @Test
    void findByUserId_shouldReturnExpenseList_whenAuthorized() throws Exception {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.findAllByUserId("user1")).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/findByUserId/user1")
                        .with(user((UserDetails) principal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("expense1"))
                .andExpect(jsonPath("$[0].userId").value("user1"));
    }

    @Test
    void addExpense_shouldReturnSavedExpenseDto() throws Exception {
        when(expenseService.save(any(Expense.class))).thenReturn(expense);

        mockMvc.perform(post("/api/expenses/add")
                        .with(user((UserDetails) principal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("expense1"))
                .andExpect(jsonPath("$.userId").value("user1"));
    }

    @Test
    void updateExpense_shouldReturnUpdatedExpenseDto() throws Exception {
        when(expenseService.update(any(Expense.class))).thenReturn(expense);

        mockMvc.perform(put("/api/expenses/update")
                        .with(user((UserDetails) principal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("expense1"))
                .andExpect(jsonPath("$.userId").value("user1"));
    }

    @Test
    void deleteExpense_shouldReturnDeletedExpenseDto() throws Exception {
        when(expenseService.delete("expense1")).thenReturn(expense);
        when(expenseService.findById("expense1")).thenReturn(expense);

        mockMvc.perform(delete("/api/expenses/delete/expense1")
                        .with(user((UserDetails) principal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("expense1"));
    }

    @Test
    void findByDateBetween_shouldReturnExpenseList() throws Exception {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.findAllByDateBetweenAndUserId(anyString(), anyString(), anyString())).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/findByDateBetween")
                        .with(user((UserDetails) principal))
                        .param("startDate", "2025-05-01T00:00:00")
                        .param("endDate", "2025-05-15T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("expense1"));
    }

    @Test
    void findByDateAfter_shouldReturnExpenseList() throws Exception {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.findAllByDateAfterAndUserId(anyString(), anyString())).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/findByDateAfter")
                        .with(user((UserDetails) principal))
                        .param("startDate", "2025-05-01T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("expense1"));
    }

    @Test
    void findByDateBefore_shouldReturnExpenseList() throws Exception {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.findAllByDateBeforeAndUserId(anyString(), anyString())).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/findByDateBefore")
                        .with(user((UserDetails) principal))
                        .param("endDate", "2025-05-15T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("expense1"));
    }
}