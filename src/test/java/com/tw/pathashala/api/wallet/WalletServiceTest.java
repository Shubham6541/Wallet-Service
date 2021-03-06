package com.tw.pathashala.api.wallet;


import com.tw.pathashala.api.transaction.Transaction;
import com.tw.pathashala.api.transaction.TransactionRepository;
import com.tw.pathashala.api.transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.tw.pathashala.api.transaction.TransactionType.*;
import static com.tw.pathashala.api.transaction.TransactionType.DEBIT;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletServiceTest {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @AfterEach
    void tearDown() {
        walletRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    void createAWallet() {
        WalletService walletService = new WalletService(walletRepository, transactionRepository);

        Wallet wallet = walletService.create(new Wallet("Walter White", 100));

        Optional<Wallet> walletFromStore = walletRepository.findById(wallet.getId());
        assertEquals("Walter White", wallet.getName());
        assertEquals("Walter White", walletFromStore.get().getName());
    }

    @Test
    void fetchAWallet() {
        Wallet savedWallet = walletRepository.save(new Wallet("Walter White", 100));
        WalletService walletService = new WalletService(walletRepository, transactionRepository);

        Wallet wallet = walletService.fetch(savedWallet.getId());

        assertEquals("Walter White", wallet.getName());
    }

    @Test
    void failsToFetchAWalletWithInvalidId() {
        long invalidId = 999L;
        WalletService walletService = new WalletService(walletRepository, transactionRepository);

        assertThrows(WalletNotFoundException.class, () -> walletService.fetch(invalidId));
    }

    @Test
    void credit50IntoTheWallet() {
        Wallet savedWallet = walletRepository.save(new Wallet("Walter White", 100));
        WalletService walletService = new WalletService(walletRepository, transactionRepository);
        Transaction newTransaction = new Transaction(CREDIT, 50);

        Transaction savedTransaction = walletService.createTransaction(newTransaction, savedWallet.getId());

        savedWallet = walletService.fetch(savedWallet.getId());
        assertEquals(1, savedWallet.getTransactions().size());
        assertEquals(150, savedWallet.getBalance());
        assertNotEquals(0, savedTransaction.getId());
    }

    @Test
    void credit50FromTheWallet() {
        Wallet savedWallet = walletRepository.save(new Wallet("Walter White", 100));
        WalletService walletService = new WalletService(walletRepository, transactionRepository);
        Transaction newTransaction = new Transaction(DEBIT, 50);

        Transaction savedTransaction = walletService.createTransaction(newTransaction, savedWallet.getId());

        savedWallet = walletService.fetch(savedWallet.getId());
        assertEquals(1, savedWallet.getTransactions().size());
        assertEquals(50, savedWallet.getBalance());
        assertEquals(1, savedTransaction.getId());
    }

    @Test
    void recentTransactionsList() {
        Wallet savedWallet = walletRepository.save(new Wallet("Walter White", 100));
        WalletService walletService = new WalletService(walletRepository, transactionRepository);
        Transaction transaction = new Transaction(CREDIT, 100);
        Transaction anotherTransaction = new Transaction(DEBIT, 50);

        Transaction savedTransaction = walletService.createTransaction(transaction, savedWallet.getId());
        Transaction anotherSavedTransaction = walletService.createTransaction(anotherTransaction, savedWallet.getId());

        savedWallet = walletService.fetch(savedWallet.getId());
        List<Transaction> recentTransactionList = walletService.transactions(savedWallet.getId(), 1);
        assertEquals(1, recentTransactionList.size());
        assertEquals(anotherSavedTransaction.getDate(), recentTransactionList.get(0).getDate());
        assertEquals(anotherSavedTransaction.getId(), recentTransactionList.get(0).getId());
    }

    @Test
    void transferMoney() {
        Wallet savedWallet = walletRepository.save(new Wallet("Walter White", 100));
        Wallet anotherSavedWallet = walletRepository.save(new Wallet("J.S.Gill", 200));
        WalletService walletService = new WalletService(walletRepository, transactionRepository);
        TransferRequest transferRequest = new TransferRequest(anotherSavedWallet.getId(), 100, "Snacks", DEBIT);

        Transaction savedTransaction = walletService.createTransaction(transferRequest, savedWallet.getId());

        savedWallet = walletService.fetch(savedWallet.getId());
        anotherSavedWallet = walletService.fetch(anotherSavedWallet.getId());

        assertEquals(1, savedWallet.getTransactions().size());
        assertEquals(0, savedWallet.getBalance());
        assertEquals(1, savedTransaction.getId());
        assertEquals(300, anotherSavedWallet.getBalance());
    }
}
