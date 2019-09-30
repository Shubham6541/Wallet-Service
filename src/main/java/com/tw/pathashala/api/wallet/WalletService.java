package com.tw.pathashala.api.wallet;

import com.tw.pathashala.api.transaction.Transaction;
import com.tw.pathashala.api.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WalletService {

    private WalletRepository walletRepository;
    private TransactionRepository transactionRepository;

    WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public Wallet create(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    Wallet fetch(Long id) {
        return walletRepository.findById(id).orElseThrow(WalletNotFoundException::new);
    }

    Transaction createTransaction(Transaction transaction, long walletId) {
        Wallet savedWallet = fetch(walletId);
        savedWallet.processTransaction(transaction);
        Wallet updatedWallet = create(savedWallet);
        List<Transaction> transactions = updatedWallet.getTransactions();
        return transactions.get(transactions.size() - 1);
    }

    List<Transaction> transactions(long walletId) {
        return transactionRepository.findAllByWalletId(walletId);
    }
}
