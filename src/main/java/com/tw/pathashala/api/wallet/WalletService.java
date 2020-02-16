package com.tw.pathashala.api.wallet;

import com.tw.pathashala.api.transaction.Transaction;
import com.tw.pathashala.api.transaction.TransactionRepository;
import com.tw.pathashala.api.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tw.pathashala.api.transaction.TransactionType.*;


@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
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

    List<Transaction> transactions(long walletId, int numberOfTransaction) {
        Pageable pageable = PageRequest.of(0, numberOfTransaction);
        return transactionRepository.findAllByWalletIdOrderByDateDesc(walletId, pageable);
    }

    Transaction createTransaction(TransferRequest transferRequest, long walletId){
        if(transferRequest.getReceiverWalletId() != 0){
            Transaction transaction = new Transaction(DEBIT, transferRequest.getAmount(),transferRequest.getRemarks());
            return transfer(transaction,walletId, transferRequest.getReceiverWalletId());
        }
        Transaction transaction = new Transaction( transferRequest.getTransactionType(),transferRequest.getAmount(),transferRequest.getRemarks());
        return createTransaction(transaction, walletId);
    }

    Transaction transfer(Transaction transaction, long walletId, long receiverWalletId) {
        Transaction response = createTransaction(transaction, walletId);
        Transaction receiverTransaction = new Transaction(CREDIT, transaction.getAmount(), transaction.getRemarks());
        createTransaction(receiverTransaction, receiverWalletId);
        return response;
    }
}
