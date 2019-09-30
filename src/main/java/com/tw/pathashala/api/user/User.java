package com.tw.pathashala.api.user;

import com.tw.pathashala.api.wallet.Wallet;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

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

    public Long walletId() {
        return wallet.getId();
    }

    void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }
}
