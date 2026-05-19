package com.infinitisky.config;

import com.infinitisky.entity.Role;
import com.infinitisky.entity.User;
import com.infinitisky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByEmail(
                "admin@gmail.com"
        ).isEmpty()) {

            User admin = User.builder()
                    .fullname("System Admin")
                    .email("admin@gmail.com")
                    .password(
                            passwordEncoder.encode("123456")
                    )
                    .role(Role.ADMIN)
                    .active(true)
                    .build();

            userRepository.save(admin);

            System.out.println("ADMIN CREATED");
        }
    }
}