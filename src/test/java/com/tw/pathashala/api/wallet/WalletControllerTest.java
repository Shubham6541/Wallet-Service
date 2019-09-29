package com.tw.pathashala.api.wallet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.pathashala.api.transaction.Transaction;
import com.tw.pathashala.api.transaction.TransactionType;
import com.tw.pathashala.api.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
@WithMockUser
class WalletControllerTest {

    private static final String BASE_PATH = "/wallets";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @MockBean
    UserRepository userRepository;

    @Test
    void createAWalletUsingWalletResource() throws Exception {
        when(walletService.create(any(Wallet.class))).thenReturn(wallet());
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(BASE_PATH + "/")
                .content("{\"name\":\"Walter White\",\"balance\":100}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(wallet())));

        verify(walletService).create(any(Wallet.class));
    }

    @Test
    void expectCreateWalletToFailWhenNameIsLongerThan12Characters() throws Exception {
        mockMvc.perform(post(BASE_PATH + "/")
                .content("{\"name\":\"ThisNameIsTooLongForWallet\",\"balance\":100}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(walletService, times(0)).create(any(Wallet.class));
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
    void createATransactionOnAWallet() throws Exception {
        Transaction savedTransaction = new Transaction(TransactionType.CREDIT, 10);
        ObjectMapper objectMapper = new ObjectMapper();
        when(walletService.createTransaction(any(Transaction.class), eq(1L)))
                .thenReturn(savedTransaction);

        mockMvc.perform(post(BASE_PATH + "/1/transactions")
                .content("{\"type\":\"CREDIT\",\"amount\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(savedTransaction)));

        verify(walletService).createTransaction(any(Transaction.class), eq(1L));
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
