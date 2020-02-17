package com.tw.pathashala.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@WithMockUser
public class UserControllerTest {
    private static final String BASE_PATH = "/user";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void registerUser() throws Exception {
        Mockito.when(userService.register(any(User.class))).thenReturn(user());
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(BASE_PATH + "/signUp")
                .content("{\"userName\":\"Johny\",\"password\":1234}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(user())));

        Mockito.verify(userService).register(any(User.class));
    }

    User user() {
        User user = new User("Johny", "1234");
        user.setId(4L);
        return user;
    }
}
