package ru.clevertec.concurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    private Server server;

    @BeforeEach
    void setup() {
        server = new Server();
    }
    @Test
    void processRequestShouldReturnCorrectValue() throws InterruptedException {
        int value = 10;
        Response expected = new Response(90);
        Response result = server.processRequest(value);
        Assertions.assertThat(result).isEqualTo(expected);
    }
    @Test
    void processRequestShouldReturnWrongValue() throws InterruptedException {
        int value = 10;
        Response expected = new Response(999);
        Response result = server.processRequest(value);
        Assertions.assertThat(result).isNotEqualTo(expected);
    }
}