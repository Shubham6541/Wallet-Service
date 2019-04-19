package com.tw.pathashala.api.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.pathashala.api.wallet.Wallet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest {
    @Test
    void expectsTransactionToHaveAllAttributesAfterSerialization() throws IOException {
        Transaction transaction = new Transaction(null, TransactionType.CREDIT, 100, null, "Snacks");
        ObjectMapper objectMapper = new ObjectMapper();

        String transactionString = objectMapper.writeValueAsString(transaction);

        assertTrue(transactionString.contains("\"amount\":100"));
        assertTrue(transactionString.contains("\"type\":\"CREDIT\""));
        assertTrue(transactionString.contains("\"remarks\":\"Snacks\""));
    }

    @Test
    void expectsTransactionToHaveAllAttributesAfterDeSerialization() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String transactionString = "{\"amount\":100,\"remarks\":\"Snacks\",\"type\":\"CREDIT\"}";

        Transaction transaction = objectMapper.readValue(transactionString, Transaction.class);

        assertEquals(100, transaction.getAmount());
        assertEquals(TransactionType.CREDIT, transaction.getType());
        assertEquals("Snacks", transaction.getRemarks());
    }

    @Test
    void expectConvertedAmountToBeNegativeForDebit() {
        Transaction transaction = new Transaction(TransactionType.DEBIT, 100);

        int convertedAmount = transaction.convertedAmount();

        assertEquals(-100, convertedAmount);
    }

    @Test
    void expectConvertedAmountToBePositiveForCredit() {
        Transaction transaction = new Transaction(TransactionType.CREDIT, 100);

        int convertedAmount = transaction.convertedAmount();

        assertEquals(100, convertedAmount);
    }

    @Test
    void expectWalletToBeAssignedForTransaction() {
        Transaction transaction = new Transaction(TransactionType.CREDIT, 100);
        Wallet wallet = new Wallet();

        transaction.linkWallet(wallet);

        assertEquals(wallet, transaction.getWallet());
    }

    @Test
    void expectDateToBeAssignedWithCurrentValue() {
        Instant oneHourBefore = Instant.now().minus(1, ChronoUnit.HOURS);
        Transaction transaction = new Transaction(TransactionType.CREDIT, 100);

        transaction.onCreate();

        assertTrue(oneHourBefore.isBefore(transaction.getDate().toInstant()));
    }

}