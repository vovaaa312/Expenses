package com.expenses.service;

import com.expenses.model.user.SystemRole;
import com.expenses.model.user.User;
import com.expenses.model.exception.UserNotFoundException;
import com.expenses.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findAuthUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findAuthUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> findAuthUserById(String id) {
        return userRepository.findAuthUsersById(id);
    }

    public List<User> getUsersByIds(List<String> userIds) {
        return userRepository.findAllById(userIds);
    }

    public Optional<User> addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));

    }

//    public Optional<AuthUser> updateAuthUser(AuthUser authUser) {
//        AuthUser existingUser = userRepository.findAuthUsersById(authUser.getId()).get();
//        existingUser.setUsername(authUser.getUsername());
//        existingUser.setEmail(authUser.getEmail());
//        existingUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
//        existingUser.setRole(authUser.getRole());
//        existingUser.setActive(authUser.isActive());
//        return Optional.of(userRepository.save(existingUser));
//    }

    public Optional<User> updateAuthUser(String userId, User authUser) {
        User existingUser = userRepository.findAuthUsersById(userId).orElseThrow();
        existingUser.setUsername(authUser.getUsername());
        existingUser.setEmail(authUser.getEmail());
        existingUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        existingUser.setRole(authUser.getRole());
        existingUser.setActive(authUser.isActive());
        return Optional.of(userRepository.save(existingUser));
    }


    public Optional<User> updateUsername(String userId, String newUsername) {
        User existingUser = userRepository.findAuthUsersById(userId).orElseThrow(()->new UserNotFoundException("User not found."));
        existingUser.setUsername(newUsername);
        return Optional.of(userRepository.save(existingUser));

    }

    public Optional<User> updateEmail(String userId, String newEmail) {
        User existingUser = userRepository.findAuthUsersById(userId).orElseThrow(()->new UserNotFoundException("User not found."));

        existingUser.setEmail(newEmail);
        return Optional.of(userRepository.save(existingUser));

    }

    public Optional<User> updatePassword(String userId, String newPassword) {
        User existingUser = userRepository.findAuthUsersById(userId).orElseThrow(()->new UserNotFoundException("User not found."));

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        return Optional.of(userRepository.save(existingUser));
    }

    public Optional<User> updateRole(String userId, String role) {
        User existingUser = userRepository.findAuthUsersById(userId).orElseThrow(()->new UserNotFoundException("User not found."));

        existingUser.setRole(SystemRole.valueOf(role));
        return Optional.of(userRepository.save(existingUser));
    }


    public Optional<User> deleteUser(String id) {

        User deleted = userRepository.findAuthUsersById(id).orElseThrow(()->new UserNotFoundException("User not found."));
        userRepository.deleteById(id);
        return Optional.of(deleted);
    }

}