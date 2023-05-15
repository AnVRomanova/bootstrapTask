package com.example31._Spring_Security.service;


import com.example31._Spring_Security.model.User;
import com.example31._Spring_Security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
   private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User readUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    @Transactional
    @Override
    public void edit(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    public User findByName(String name) {
        return userRepository.findByName(name);
    }

}
