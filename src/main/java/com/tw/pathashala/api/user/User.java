package com.tw.pathashala.api.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "usertable")
public class User {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        setPassword(password);
    }

    public User() {
    }

    String getUserName() {
        return userName;
    }

    String getPassword() {
        return password;
    }

    Long getId() {
        return id;
    }

    void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }
}
