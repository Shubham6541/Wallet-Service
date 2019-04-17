package com.tw.pathashala.wallet.model;

import com.fasterxml.jackson.databind.ObjectMapper;
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

}