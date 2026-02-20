package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final Map<Long, UserEntity> storage = new HashMap<>();

    public void save(UserEntity user) { storage.put(user.getId(), user); }

    public UserEntity findById(Long id) { return storage.get(id); }

    public List<UserEntity> findAll() { return new ArrayList<>(storage.values()); }

    public void delete(Long id) { storage.remove(id); }
}