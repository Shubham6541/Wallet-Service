package com.tw.pathashala.api.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Nested
    class LoadUserByUsername {
        @Test
        void shouldLoadWhenUserExists() {
            User user = new User("John", "FooBar");
            userRepository.save(user);

            UserDetails userDetails = userService.loadUserByUsername("John");
            assertEquals("John", userDetails.getUsername());
        }

        @Test
        void shouldNotLoadWhenUserDoesNotExist() {
            assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("Jane"));
        }

        @Test
        void shouldRegisterUser() {
            User user = new User("John", "FooBar");
            userService.register(user);
            UserDetails userDetails = userService.loadUserByUsername("John");

            assertEquals("John", userDetails.getUsername());
        }
    }
}