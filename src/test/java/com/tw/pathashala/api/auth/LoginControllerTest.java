package com.tw.pathashala.api.auth;

import com.tw.pathashala.api.transaction.TransactionRepository;
import com.tw.pathashala.api.user.UserRepository;
import com.tw.pathashala.api.wallet.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    WalletRepository walletRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @Test
    @WithMockUser
    void shouldLoginWhenValid() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}