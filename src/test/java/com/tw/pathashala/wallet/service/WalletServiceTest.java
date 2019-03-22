package com.tw.pathashala.wallet.service;


import com.tw.pathashala.wallet.model.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WalletServiceTest {

    @Autowired
    WalletRepository walletRepository;

    @AfterEach
    void tearDown() {
        walletRepository.deleteAll();
    }

    @Test
    void createAWallet() {
        WalletService walletService = new WalletService(walletRepository);
        Wallet wallet = walletService.create(new Wallet("Walter White", 100));
        Optional<Wallet> walletFromStore = walletRepository.findById(wallet.getId());

        assertEquals("Walter White", wallet.getName());
        assertEquals("Walter White", walletFromStore.get().getName());
    }

    @Test
    void fetchAWallet() {
        walletRepository.save(new Wallet("Walter White", 100));

        WalletService walletService = new WalletService(walletRepository);
        Wallet wallet = walletService.fetch(1L);
        assertEquals("Walter White", wallet.getName());
    }
}
