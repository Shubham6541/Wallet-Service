package com.tw.pathashala.wallet.service;


import com.tw.pathashala.wallet.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class WalletServiceTest {

    @Autowired
    WalletService walletService;

    @Autowired
    WalletRepository walletRepository;

    @Test
    void createAWallet() {

        Wallet wallet = walletService.create(new Wallet("Walter White", 100));
        Optional<Wallet> walletFromStore = walletRepository.findById(wallet.getId());

        assertEquals("Walter White", wallet.getName());
        assertEquals("Walter White", walletFromStore.get().getName());
    }
}
