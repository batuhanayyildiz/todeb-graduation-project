package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CustomJwtException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Role;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.User;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.UserRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

class UserServiceTest {
    private  UserRepository userRepository;

    private  PasswordEncoder passwordEncoder;

    private  JwtTokenProvider jwtTokenProvider;

    private  AuthenticationManager authenticationManager;

    private UserService userService;

    @BeforeEach
    public void setup()
    {
        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder= Mockito.mock(PasswordEncoder.class);
        jwtTokenProvider=Mockito.mock(JwtTokenProvider.class);
        authenticationManager=Mockito.mock(AuthenticationManager.class);
        userService=new UserService(userRepository,passwordEncoder,jwtTokenProvider,authenticationManager);



    }

    @Test
    void testGetAll_WhenUsersExist_ShouldReturnListOfCustomers() {

        User user1= new User(1,"ali","ali@mail.com","ali", Collections.emptyList());
        User user2= new User(2,"veli","veli@mail.com","veli", Collections.emptyList());
        User user3= new User(3,"cenk","cenk@mail.com","cenk", Collections.emptyList());

        List<User> expectedUsers= new ArrayList<>();
        Collections.addAll(expectedUsers,user1,user2,user3);

        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> result= userService.getAll();

        assertEquals(result,
                expectedUsers);
        Mockito.verify(userRepository).findAll();

    }
    @Test
    void testGetAll_WhenUsersDoNotExist_ShouldReturnEmptyList() {


        List<User> expectedUsers= new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result= userService.getAll();

        assertEquals(result,
                expectedUsers);
        Mockito.verify(userRepository).findAll();

    }

    @Test
    void testSignin_whenUserExists_shouldReturnString()
    {
        String username="admin";
        String password="pass";
        String expectedToken = "token";


        User user= new User(1,"ali","ali@mail.com","ali", Collections.emptyList());


        Mockito.when(authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);
        Mockito.when(jwtTokenProvider.createToken(username,Collections.emptyList()))
                .thenReturn(expectedToken);


        String result=userService.signin(username,password);

        assertEquals(result,
                expectedToken);

        Mockito.verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Mockito.verify(jwtTokenProvider).createToken(username,Collections.emptyList());
    }

    @Test
    void testSignin_whenUserDoesNotExist_shouldReturnString()
    {
        String username="admin";
        String password="pass";



        Mockito.when(authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(username, password)))
                .thenThrow(new CustomJwtException("Invalid username/password supplied", HttpStatus.BAD_REQUEST));


        assertThrows(CustomJwtException.class,
                () -> userService.signin(username,password));

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verifyNoInteractions(userRepository);
        verifyNoInteractions(jwtTokenProvider);

    }

    @Test
    void signup() {
    }

    @Test
    void delete() {
    }

    @Test
    void search() {
    }

    @Test
    void whoami() {
    }

    @Test
    void refresh() {
    }
}