package com.expenses.model.dTo;

import com.expenses.model.user.SystemRole;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private SystemRole role;
    private boolean active;
}
