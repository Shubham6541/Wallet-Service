package com.tw.pathashala.api.wallet;

import com.tw.pathashala.api.transaction.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wallets")
@CrossOrigin
class WalletController {
    private static Logger logger = LogManager.getLogger(WalletController.class);

    private final WalletService walletService;

    public WalletController(@Autowired WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Wallet create(@Valid @RequestBody Wallet wallet) {
        logger.info(() -> "Creating api with amount: " + wallet.getBalance());
        return walletService.create(wallet);
    }

    @GetMapping("/{id}")
    Wallet fetch(@PathVariable Long id) {
        return walletService.fetch(id);
    }

    @PostMapping("/{walletId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    Transaction createTransaction(@RequestBody TransferRequest transferRequest, @PathVariable long walletId) {
        return walletService.createTransaction(transferRequest, walletId);
    }

    @GetMapping("/{walletId}/transactions/{pageNumber}")
    List<Transaction> listTransactions(@PathVariable long walletId, int pageNumber) {
        return walletService.transactions(walletId,pageNumber);
    }

    @PostMapping("/{walletId}/transfer/{receiverWalletId}")
    @ResponseStatus(HttpStatus.CREATED)
    Transaction transfer(@RequestBody Transaction transaction, @PathVariable long walletId, @PathVariable long receiverWalletId) {
        return walletService.transfer(transaction, walletId, receiverWalletId);
    }


}
