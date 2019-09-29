package com.tw.pathashala.api.wallet;

import com.tw.pathashala.api.transaction.Transaction;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wallet {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 1, value = "api id")
    Long id;

    @ApiModelProperty(position = 2, required = true, value = "Username")
    @Size(min = 5, max = 12, message = "The category name must be {min} to {max} characters in length.")
    private String name;

    @ApiModelProperty(position = 4, required = true, value = "Amount present in the api, should not be negative")
    private int balance;

    @ApiModelProperty(position = 3, value = "Set of Transactions of the api ")
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();


    public Wallet() {
    }

    Wallet(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void processTransaction(Transaction updatedTransaction) {
        this.balance += updatedTransaction.convertedAmount();
        this.transactions.add(updatedTransaction);
        updatedTransaction.linkWallet(this);
    }

}