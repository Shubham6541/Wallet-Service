package com.tw.pathashala.wallet.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.pathashala.wallet.WalletNotFoundException;
import com.tw.pathashala.wallet.model.Wallet;
import com.tw.pathashala.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
class WalletControllerTest {

    private static final String BASE_PATH = "/wallets";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    void createAWalletUsingWalletResource() throws Exception {
        Mockito.when(walletService.create(any(Wallet.class))).thenReturn(wallet());
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(BASE_PATH + "/")
                .content("{\"name\":\"Walter White\",\"balance\":100}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(wallet())));

        Mockito.verify(walletService).create(any(Wallet.class));
    }

    @Test
    void expectCreateWalletToFailWhenNameIsLongerThan12Characters() throws Exception {
        mockMvc.perform(post(BASE_PATH + "/")
                .content("{\"name\":\"ThisNameIsTooLongForWallet\",\"balance\":100}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(walletService, times(0)).create(any(Wallet.class));
    }

    @Test
    void shouldGetAWallet() throws Exception {
        when(walletService.fetch(1L)).thenReturn(wallet());
        mockMvc.perform(get(BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("100"));

    }

    @Test
    void shouldReturnNotFoundWhenWalletDoesNotExist() throws Exception {
        when(walletService.fetch(12L)).thenThrow(new WalletNotFoundException());
        mockMvc.perform(get(BASE_PATH + "/12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void expectCrossOriginRequestsToBeSuccessful() throws Exception {
        this.mockMvc
                .perform(get("/wallets/1")
                        .header("Origin", "http://dumyurl.com"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "*"));
    }



    private Wallet wallet() {
        Wallet wallet = new Wallet("Walter White", 100);
        wallet.setId(1L);
        return wallet;
    }
}
