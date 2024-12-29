package com.example.demo.config;

import com.example.demo.entity.RoleEntity;
import com.example.demo.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByRole("ROLE_USER").isEmpty()) {
            RoleEntity userRole = new RoleEntity();
            userRole.setRole("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByRole("ROLE_ADMIN").isEmpty()) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setRole("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

    }
}
