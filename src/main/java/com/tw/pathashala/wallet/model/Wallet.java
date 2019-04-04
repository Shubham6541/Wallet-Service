package com.tw.pathashala.wallet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Wallet {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 5, max = 12, message = "The category name must be {min} to {max} characters in length.")
    private String name;

    private int balance;

    public Wallet() {
    }

    public Wallet(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }


    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
