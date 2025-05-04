package com.expenses.controller;

import com.expenses.model.dTo.UserDto;
import com.expenses.model.dTo.mapper.UserMapper;
import com.expenses.model.user.SystemRole;
import com.expenses.model.user.User;
import com.expenses.service.AuthService;
import com.expenses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

//    @PreAuthorize("hasAuthority('admin:read')")
//    @GetMapping("/getAll")
//    public ResponseEntity<?> getAllUsers() {
//        List<UserDto> userDtos = userService
//                .findAll()
//                .stream()
//                .map(User::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(userDtos);
//    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {

        return ResponseEntity.ok(UserMapper.toDtoList(userService.findAll()));
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(UserMapper.toDto(userService.findUserById(id)));
    }


    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestBody UserDto userDto) {
//        if (userService.findAuthUserByUsername(user.getUsername()).isPresent()) {
//            String errorMessage = "User with username: {" + user.getUsername() + "} already exists";
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
//        }
//        if(userService.findAuthUserByEmail(user.getEmail()).isPresent()){
//            String errorMessage = "User with email: {" + user.getEmail() + "} already exists";
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
//        }

        User updatedUser = userService.updateUser(userDto.getId(), UserMapper.toEntity(userDto));
        return ResponseEntity.ok(UserMapper.toDto(updatedUser));
    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        return ResponseEntity
                .ok(userService.addUser(UserMapper.toEntity(userDto)));
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        return ResponseEntity
                .ok(UserMapper.toDto(userService.deleteUser(id)));
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/isAdmin")
//    public ResponseEntity<Boolean> isAdmin(@RequestHeader("Authorization") String authHeader) {
//        User authenticatedUser = authService.getAuthenticatedUser(authHeader);
//        boolean isAdmin = authenticatedUser.getRole().equals(SystemRole.SYSTEM_ADMIN);
//        return ResponseEntity.ok(isAdmin);
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(@AuthenticationPrincipal User principal) {
            return ResponseEntity.ok(principal.isAdmin());
    }

}