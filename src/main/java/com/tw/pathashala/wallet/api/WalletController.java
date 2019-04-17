package com.tw.pathashala.wallet.api;

import com.tw.pathashala.wallet.model.Wallet;
import com.tw.pathashala.wallet.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        logger.info(() -> "Creating wallet with amount: " + wallet.getBalance());
        return walletService.create(wallet);
    }

    @GetMapping("/{id}")
    Wallet fetch(@PathVariable Long id) {
        return walletService.fetch(id);
    }
}
