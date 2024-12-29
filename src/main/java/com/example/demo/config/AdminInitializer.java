package com.example.demo.config;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String email = "admin@gmail.com";
        String password = "admin";
        String username = "admin";

        if (userRepository.findByEmail(email).isEmpty()) {

            RoleEntity adminRole = roleRepository.findByRole("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

            UserEntity admin = new UserEntity();

            admin.setEmail(email);
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
            System.out.println("Admin created");
        } else {
            System.out.println("Admin existed");
        }
    }
}
