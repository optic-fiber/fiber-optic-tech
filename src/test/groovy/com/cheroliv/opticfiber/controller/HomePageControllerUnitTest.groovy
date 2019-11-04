package com.cheroliv.opticfiber.controller

import com.cheroliv.opticfiber.service.InterService
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc

import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Slf4j
@TypeChecked
@WithMockUser
@WebMvcTest(HomePageController)
@TestMethodOrder(OrderAnnotation)
@DisplayName("InterControllerUnitTest")
class HomePageControllerUnitTest {
    @BeforeAll
    static void init() { initMocks(this) }
    @Autowired
    MockMvc mockMvc
    @MockBean
    InterService interService

    @Test
    void testHome() {
        mockMvc.perform(get('/'))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
    }

    @Test
    void testSubmitInter() {
    }
}
