package com.hotela;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class HotelaApplicationTests {

    @Test
    void simpleTest() {
        assertEquals(1, 1);
    }
}