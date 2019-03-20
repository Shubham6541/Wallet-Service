package com.tw.pathashala.wallet.api;


import com.tw.pathashala.wallet.model.Wallet;
import com.tw.pathashala.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WalletController.class)
class WalletControllerTest {

    private static final String BASE_PATH = "/wallets";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    void createAWalletUsingWalletResource() throws Exception {
        Wallet expectedWallet = new Wallet("Walter White", 100);
        Mockito.when(walletService.create(any(Wallet.class))).thenReturn(expectedWallet);

        mockMvc.perform(post(BASE_PATH + "/")
                .content("{\"name\":\"Walter White\",\"balance\":100}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("100"));

        Mockito.verify(walletService).create(any(Wallet.class));
    }
}
