package com.expenses.controller;

import com.expenses.model.dTo.UserDto;
import com.expenses.model.user.SystemRole;
import com.expenses.model.user.User;
import com.expenses.service.AuthService;
import com.expenses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
//@PreAuthorize("hasRole('SYSTEM_ADMIN')")
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService
                .findAll()
                .stream()
                .map(User::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }


    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
//        if (userService.findAuthUserByUsername(user.getUsername()).isPresent()) {
//            String errorMessage = "User with username: {" + user.getUsername() + "} already exists";
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
//        }
//        if(userService.findAuthUserByEmail(user.getEmail()).isPresent()){
//            String errorMessage = "User with email: {" + user.getEmail() + "} already exists";
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
//        }

        User updatedUser = userService.updateUser(userDto.getId(), User.fromDto(userDto));
        return ResponseEntity.ok(User.toDto(updatedUser));    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.findUserByUsername(user.getUsername()) != null) {
            String errorMessage = "User with username: {" + user.getUsername() + "} already exists";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        if (userService.findUserByEmail(user.getEmail()) != null) {
            String errorMessage = "User with email: {" + user.getEmail() + "} already exists";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        return ResponseEntity
                .ok(userService.addUser(user));
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        return ResponseEntity
                .ok(userService.deleteUser(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(@RequestHeader("Authorization") String authHeader) {
        User authenticatedUser = authService.getAuthenticatedUser(authHeader);
        boolean isAdmin = authenticatedUser.getRole().equals(SystemRole.SYSTEM_ADMIN);
        return ResponseEntity.ok(isAdmin);
    }

}