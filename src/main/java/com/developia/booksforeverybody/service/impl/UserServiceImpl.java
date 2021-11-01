package com.developia.booksforeverybody.service.impl;

import com.developia.booksforeverybody.dao.entity.Role;
import com.developia.booksforeverybody.dao.entity.RoleEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.entity.UserStatus;
import com.developia.booksforeverybody.dao.repository.RoleRepository;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.UserAlreadyExistsException;
import com.developia.booksforeverybody.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void register(UserEntity user) {

        userRepository.findUserByUsername(user.getUsername()).ifPresent(
                u -> {
                    throw new UserAlreadyExistsException("Username already exists");
                }
        );

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        RoleEntity role = roleRepository.findByName(Role.USER);

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
    }
}
