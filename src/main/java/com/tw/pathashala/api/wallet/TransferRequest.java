package com.tw.pathashala.api.wallet;

import com.tw.pathashala.api.transaction.TransactionType;

class TransferRequest {
    private Long receiverWalletId;
    private int amount;
    private String remarks;
    private TransactionType transactionType;

    public TransferRequest(long receiverWalletId, int amount, String remarks, TransactionType transactionType) {
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
        this.remarks = remarks;
        this.transactionType = transactionType;
    }

    public TransferRequest() {
    }

    public long getReceiverWalletId() {
        return receiverWalletId;
    }

    public int getAmount() {
        return amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
