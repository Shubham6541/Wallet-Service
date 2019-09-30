package com.tw.pathashala.api;

import com.tw.pathashala.api.user.User;
import com.tw.pathashala.api.user.UserRepository;
import com.tw.pathashala.api.wallet.Wallet;
import com.tw.pathashala.api.wallet.WalletService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner initDatabase(UserRepository repository, WalletService walletService) {
        return args -> {
            if(repository.findByUserName("seed-user-1").isEmpty()) {
                User savedUser = repository.save(new User("seed-user-1", "foobar"));
                walletService.create(new Wallet("Savings", 0, savedUser));
            }
            if(repository.findByUserName("seed-user-2").isEmpty()) {
                User savedUser = repository.save(new User("seed-user-2", "foobar"));
                walletService.create(new Wallet("Savings", 0, savedUser));
            }
        };
    }
}
