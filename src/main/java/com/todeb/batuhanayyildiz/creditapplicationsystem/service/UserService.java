package com.todeb.batuhanayyildiz.creditapplicationsystem.service;


import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CustomJwtException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Role;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.User;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.UserRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;


    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    public String signin(String username, String password)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomJwtException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public String signup(User user, boolean isAdmin)
    {
        if (!userRepository.existsByUsername(user.getUsername()))
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = isAdmin ? Role.ROLE_ADMIN : Role.ROLE_USER;
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        } else {
            throw new CustomJwtException("Username is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(String username)
    {
        User byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            throw new NotFoundException("username : " + username);
        } else if (!byUsername.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("No permission to delete user : " + username);
        }
        userRepository.deleteByUsername(username);
    }

    public User search(String username)
    {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomJwtException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }


}
