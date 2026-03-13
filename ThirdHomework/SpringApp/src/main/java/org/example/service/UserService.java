package org.example.service;

import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String name, LocalDate birthDate) {
        try {
            UserEntity user = new UserEntity();
            user.setId(UUID.randomUUID());
            user.setName(name);
            user.setBirthDate(birthDate);

            userRepository.save(user);
            System.out.println("Пользователь создан: " + name + " (ID: " + user.getId() + ")");
        } catch (DuplicateKeyException e) {
            System.err.println("Ошибка: пользователь с таким ID уже существует");
        } catch (DataAccessException e) {
            System.err.println("Ошибка базы данных: " + e.getMessage());
        }
    }

    public UserEntity getUser(UUID id) {
        try {
            return userRepository.findById(id);
        } catch (DataAccessException e) {
            System.err.println("Ошибка при получении пользователя: " + e.getMessage());
            return null;
        }
    }

    public List<UserEntity> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            System.err.println("Ошибка при получении списка: " + e.getMessage());
            return List.of();
        }
    }

    public void updateUser(UUID id, String newName, LocalDate newBirthDate) {
        try {
            UserEntity user = userRepository.findById(id);
            if (user != null) {
                user.setName(newName);
                user.setBirthDate(newBirthDate);
                userRepository.save(user);
                System.out.println("Пользователь обновлен: " + id);
            } else {
                System.out.println("Пользователь с ID " + id + " не найден");
            }
        } catch (DataAccessException e) {
            System.err.println("Ошибка при обновлении: " + e.getMessage());
        }
    }

    public void deleteUser(UUID id) {
        try {
            userRepository.delete(id);
            System.out.println("Пользователь удален: " + id);
        } catch (DataAccessException e) {
            System.err.println("Ошибка при удалении: " + e.getMessage());
        }
    }
}