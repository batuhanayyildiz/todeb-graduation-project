package com.todeb.batuhanayyildiz.creditapplicationsystem;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.User;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.UserRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleDataInitiliazer implements ApplicationRunner {


    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) {
//

        // Creating a sample Admin USER
        User adminUser = new User("admin-user", "adminuser@mail.com", "pass");

        if(adminUser.getUsername() != null && !adminUser.getUsername().isEmpty()){
            // @NotNull && @NotEmpty = @NotBlank
        }

        if (!userRepository.existsByUsername(adminUser.getUsername())) {
            userService.signup(adminUser, true);
        }

        // Creating a sample USER
        User sampleUser = new User("client-user", "clientuser@mail.com", "pass");
        if (!userRepository.existsByUsername(sampleUser.getUsername())) {
            userService.signup(sampleUser, false);
        }

    }

}
