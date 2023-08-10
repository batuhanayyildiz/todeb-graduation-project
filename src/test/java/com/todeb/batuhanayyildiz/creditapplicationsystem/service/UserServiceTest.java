package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CustomJwtException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Role;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.User;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.UserRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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
    public void setUp()
    {
        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder= Mockito.mock(PasswordEncoder.class);
        jwtTokenProvider=Mockito.mock(JwtTokenProvider.class);
        authenticationManager=Mockito.mock(AuthenticationManager.class);
        userService=new UserService(userRepository,passwordEncoder,jwtTokenProvider,authenticationManager);



    }

    @Test
    void testGetAll_ifUsersExist_ShouldReturnListOfCustomers() {

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
    void testGetAll_ifUsersDogitNotExist_ShouldReturnEmptyList() {


        List<User> expectedUsers= new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result= userService.getAll();

        assertEquals(result,
                expectedUsers);
        Mockito.verify(userRepository).findAll();

    }

    @Test
    void testSignin_ifUserExists_shouldReturnString()
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
    void testSignin_ifUserDoesNotExist_shouldThrowCustomJwtException()
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
    void testSignup_ifUserDoesNotExist_shouldReturnString()
    {
        boolean isAdmin=true;
        String expectedToken="token";
        User user= new User(1,"ali","ali@mail.com","ali", List.of(Role.ROLE_ADMIN));
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(Boolean.FALSE);
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("ali");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(jwtTokenProvider.createToken(user.getUsername(), user.getRoles())).thenReturn("token");


        String result=userService.signup(user,isAdmin);

        assertEquals(result,
                expectedToken);

        Mockito.verify(userRepository).existsByUsername(user.getUsername());
        Mockito.verify(passwordEncoder).encode(user.getPassword());

        Mockito.verify(userRepository).save(user);
        Mockito.verify(jwtTokenProvider).createToken(user.getUsername(), user.getRoles());



    }

    @Test
    void testSignup_ifUserExists_shouldThrowCustomJwtException()
    {
        boolean isAdmin=true;
        String expectedToken="token";
        User user= new User(1,"ali","ali@mail.com","ali", List.of(Role.ROLE_ADMIN));
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(Boolean.TRUE);

        assertThrows(CustomJwtException.class,
                () -> userService.signup(user,isAdmin));

        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(jwtTokenProvider);
    }

    @Test
    void testDelete_ifUserDoesNotFind_shouldThrowNotFoundException()
    {
        String username="username";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(NotFoundException.class,
                () -> userService.delete(username));

        Mockito.verify(userRepository, never()).deleteByUsername(username);

    }
    @Test
    void testDelete_ifUserHasNoAccess_shouldThrowAccessDeniedException()
    {
        User user= new User(1,"ali","ali@mail.com","ali", Collections.singletonList(Role.ROLE_USER));
        String username=user.getUsername();
        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);

        assertThrows(AccessDeniedException.class,
                () -> userService.delete(username));

    }

    @Test
    void testDelete_ifUserIsFoundAndHasAccess_shouldThrowAccessDeniedException()
    {
        User user= new User(1,"ali","ali@mail.com","ali", Collections.singletonList(Role.ROLE_ADMIN));
        String username=user.getUsername();
        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);
        Mockito.doNothing().when(userRepository).deleteByUsername(username);

        userService.delete(username);

        Mockito.verify(userRepository).deleteByUsername(username);


    }

    @Test
    void testSearch_ifUserIsFound_shouldReturnUser()
    {
        User user= new User(1,"ali","ali@mail.com","ali", Collections.singletonList(Role.ROLE_ADMIN));
        String username=user.getUsername();
        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);

        User result=userService.search(username);

        assertEquals(result,
                user);
    }
    @Test
    void testSearch_ifUserIsNotFound_shouldReturnCustomJwtException()
    {
        String username="no_user";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(CustomJwtException.class,
                () -> userService.search(username));
    }

}