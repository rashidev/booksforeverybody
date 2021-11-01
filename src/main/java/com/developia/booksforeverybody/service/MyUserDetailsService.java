package com.developia.booksforeverybody.service;

import com.developia.booksforeverybody.dao.entity.RoleEntity;
import com.developia.booksforeverybody.dao.entity.UserEntity;
import com.developia.booksforeverybody.dao.repository.UserRepository;
import com.developia.booksforeverybody.exception.UsernameOrPasswordIncorrectException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(
                        () -> {
                            throw new UsernameOrPasswordIncorrectException("Incorrect username or password");
                        }
                );

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }

        User springUser = new User(user.getUsername(),
                user.getPassword(), authorities);
        return springUser;
    }
}
