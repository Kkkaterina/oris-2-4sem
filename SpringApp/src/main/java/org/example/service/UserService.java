package org.example.service;

import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(Long id, String name) {
        userRepository.save(new UserEntity(id, name));
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long id, String newName) {
        UserEntity user = userRepository.findById(id);
        if (user != null) {
            user.setName(newName);
        }
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
