package com.tw.pathashala.wallet.service;

import com.tw.pathashala.wallet.model.Wallet;
import org.springframework.stereotype.Service;


@Service
public class WalletService {

    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet create(Wallet wallet) {
        return walletRepository.save(wallet);
    }
}
