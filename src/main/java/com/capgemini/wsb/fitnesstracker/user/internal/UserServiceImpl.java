package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final String USER = "user";

    UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        validateUserEmail(userDto);
        log.info("Creating User {}", userDto);
        if (userDto.id() != null) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserSimpleDto> getSimpleUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserMapper::toSimpleDto)
                .collect(Collectors.toList());
    }

    public void updateUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(USER));
        Optional<User> userByEmail = userRepository.findUserByEmail(userDto.email());

        if (userByEmail.isPresent() && !Objects.equals(userByEmail.get().getId(), id)) {
            validateUserEmail(userDto);
        }

        user.setEmail(userDto.email());
        user.setBirthdate(userDto.birthdate());
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void validateUserEmail(UserDto userDto) {
        Optional<User> user = userRepository.findUserByEmail(userDto.email());
        if (user.isPresent()) {
            throw new EntityNotFoundException(userDto.email());
        }
    }

    public List<UserDto> getUserOlderThan(LocalDate date) {
        return userRepository.getUserByBirthdateLessThan(date);
    }
}