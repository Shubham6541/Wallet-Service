package com.tw.pathashala.api.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.pathashala.api.transaction.Transaction;
import com.tw.pathashala.api.transaction.TransactionType;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WalletTest {

    @Test
    void expectsWalletWithNameAndBalanceAfterSerialization() throws IOException {
        Wallet wallet = new Wallet("Alice", 100);
        ObjectMapper objectMapper = new ObjectMapper();

        String walletString = objectMapper.writeValueAsString(wallet);

        assertTrue(walletString.contains("\"name\":\"Alice\""));
        assertTrue(walletString.contains("\"balance\":100"));
    }

    @Test
    void expectsWalletWithNameAndBalanceAfterDeSerialization() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String walletString = "{\"name\":\"Alice\",\"balance\":100}";

        Wallet wallet = objectMapper.readValue(walletString, Wallet.class);

        assertEquals(100, wallet.getBalance());
        assertEquals("Alice", wallet.getName());
    }

    @Test
    void expectWalletBalanceToIncreaseBy100OnCreditTransctionOf100() {
        Wallet wallet = new Wallet("Alice", 100);
        Transaction creditOf100 = new Transaction(TransactionType.CREDIT, 100);

        wallet.processTransaction(creditOf100);

        assertEquals(200, wallet.getBalance());
        assertEquals(1, wallet.getTransactions().size());
        assertEquals(creditOf100, wallet.getTransactions().get(0));
        assertEquals(wallet, wallet.getTransactions().get(0).getWallet());
    }
}