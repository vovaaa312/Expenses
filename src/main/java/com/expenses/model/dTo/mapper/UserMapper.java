package com.expenses.model.dTo.mapper;

import com.expenses.model.dTo.UserDto;
import com.expenses.model.user.User;

import java.util.*;
import java.util.stream.*;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }

    public static List<UserDto> toDtoList(List<User> users) {
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public static List<User> toEntityList(List<UserDto> userDtos) {
        return userDtos.stream().map(UserMapper::toEntity).collect(Collectors.toList());
    }
}
