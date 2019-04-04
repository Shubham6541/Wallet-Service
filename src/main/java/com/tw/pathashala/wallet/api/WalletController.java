package com.tw.pathashala.wallet.api;

import com.tw.pathashala.wallet.model.Wallet;
import com.tw.pathashala.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallets")
class WalletController {

    private final WalletService walletService;

    public WalletController(@Autowired WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    Wallet create(@Valid @RequestBody Wallet wallet) {
        return walletService.create(wallet);
    }

    @GetMapping("/{id}")
    Wallet fetch(@PathVariable Long id) {
        return walletService.fetch(id);
    }
}
