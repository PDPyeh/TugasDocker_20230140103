package com.praktikum6.deploy.services;

import com.praktikum6.deploy.model.User;  // ✅ PERBAIKAN
import com.praktikum6.deploy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServices {
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User request) {
        return userRepository.save(request);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(Long.parseLong(id)).orElse(null);
    }

    public User updateUser(String id, User request) {
        User existingUser = userRepository.findById(Long.parseLong(id)).orElse(null);

        if (existingUser != null) {
            existingUser.setNama(request.getNama());          // ✅ PERBAIKAN
            existingUser.setNim(request.getNim());
            existingUser.setJenisKelamin(request.getJenisKelamin());  // ✅ PERBAIKAN
            return userRepository.save(existingUser);
        }

        return null;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(Long.parseLong(id));
    }
}
