package com.tw.pathashala.wallet.data;

import com.tw.pathashala.wallet.model.Wallet;
import com.tw.pathashala.wallet.service.WalletRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(WalletRepository repository) {
        return args -> {
            repository.save(new Wallet("Walter White", 424));
            repository.save(new Wallet("Jess Pinkman", 100));
        };
    }
}
