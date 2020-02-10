package com.tw.pathashala.api.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    void checkLastTransaction() {
        Transaction transaction = new Transaction(TransactionType.CREDIT, 100);
        transactionRepository.save(transaction);

        //transactionRepository.find
    }
}
