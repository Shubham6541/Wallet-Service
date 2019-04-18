package com.tw.pathashala.wallet.service;

import com.tw.pathashala.wallet.WalletNotFoundException;
import com.tw.pathashala.wallet.model.Transaction;
import com.tw.pathashala.wallet.model.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WalletService {

    private WalletRepository walletRepository;

    WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet create(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet fetch(Long id) {
        return walletRepository.findById(id).orElseThrow(WalletNotFoundException::new);
    }

   public Transaction createTransaction(Transaction transaction, long walletId) {
        Wallet savedWallet = fetch(walletId);
        savedWallet.processTransaction(transaction);
        Wallet updatedWallet = create(savedWallet);
        List<Transaction> transactions = updatedWallet.getTransactions();
        return transactions.get(transactions.size() - 1);
    }
}
