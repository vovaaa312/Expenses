package com.expenses.service;

import com.expenses.model.exception.UserNotFoundException;
import com.expenses.model.user.SystemRole;
import com.expenses.model.user.User;
import com.expenses.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElse(null);    }

    public User findUserByUsername(String username) {

        return userRepository.findUserByUsername(username)
                .orElse(null);

    }

    public User findUserById(String id) {
        return userRepository.findUserById(id).orElse(null);
    }

    public User addUser(User user) {
        if(user.getPassword().length()<4 || user.getPassword().length()>16){
            throw new IllegalArgumentException("Password length must be between 4 and 16 characters");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public User updateUser(String userId, User user) {
        User existingUser = userRepository.findUsersById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));


        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

//        boolean passNotNull = user.getPassword() != null;
//        boolean passNotEmpty = !user.getPassword().isEmpty();
//        boolean passNotEqualOldPass = !user.getPassword().equals(existingUser.getPassword());
//        boolean passEqualOldPass = passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
//boolean passLength = user.getPassword().length() > 4 && user.getPassword().length() < 16;
        if (user.getPassword().length()<16) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        existingUser.setRole(user.getRole());
        existingUser.setActive(user.isActive());

        return userRepository.save(existingUser);
    }

    public User updateUsername(String userId, String newUsername) {
        User existingUser = userRepository.findUsersById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));
        existingUser.setUsername(newUsername);
        return userRepository.save(existingUser);

    }

    public User updateEmail(String userId, String newEmail) {
        User existingUser = userRepository.findUsersById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

        existingUser.setEmail(newEmail);
        return userRepository.save(existingUser);

    }

    public User updatePassword(String userId, String newPassword) {
        User existingUser = userRepository.findUsersById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(existingUser);
    }

    public User updateRole(String userId, String role) {
        User existingUser = userRepository.findUsersById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

        existingUser.setRole(SystemRole.valueOf(role));
        return userRepository.save(existingUser);
    }


    public User deleteUser(String id) {

        User deleted = userRepository.findUsersById(id).orElseThrow(() -> new UserNotFoundException("User not found."));
        userRepository.deleteById(id);
        return deleted;
    }

}