package com.expenses.model.dTo;

import com.expenses.model.user.SystemRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private SystemRole role;
    private boolean active;
}
