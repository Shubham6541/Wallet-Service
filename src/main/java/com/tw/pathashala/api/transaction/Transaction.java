package com.tw.pathashala.api.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tw.pathashala.api.wallet.Wallet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@ApiModel(description = "Transactions")
public class Transaction {
    @ApiModelProperty(position = 1, required = true, value = "transaction id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(position = 2, required = true, value = "transaction amount")
    private int amount;

    @ApiModelProperty(position = 3, required = true, value = "api id for each transaction")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    @JsonIgnore
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(position = 4, required = true, value = "Type of Transaction")
    private TransactionType type;

    @ApiModelProperty(position = 5, value = "1995-12-31 (YYYY-MM-DD)")
    private Date date;

    @ApiModelProperty(position = 6, value = "Remarks")
    private String remarks;

    Transaction(Wallet wallet, TransactionType type, int amount, Date date, String remarks) {
        this.wallet = wallet;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.remarks = remarks;
    }

    public Transaction(TransactionType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getRemarks() {
        return remarks;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void linkWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public int convertedAmount() {
        return this.getType() == TransactionType.DEBIT ? -1 * this.amount : this.amount;
    }

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

}

