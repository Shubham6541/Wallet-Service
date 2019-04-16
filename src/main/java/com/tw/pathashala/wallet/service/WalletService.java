package com.tw.pathashala.wallet.service;

import com.tw.pathashala.wallet.WalletNotFoundException;
import com.tw.pathashala.wallet.model.Wallet;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class WalletService {

    private WalletRepository walletRepository;

    WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet create(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet fetch(Long id) {
        return walletRepository.findById(id).orElseThrow(WalletNotFoundException::new);
    }
}
