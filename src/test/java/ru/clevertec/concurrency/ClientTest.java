package ru.clevertec.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientTest {
    @Mock
    Server server;
    Client client;
    @BeforeEach
    void setup() {
        client = new Client(server,List.of(new Request(90), new Request(80),new Request(70),new Request(60)));
    }

    @Test
    void sendRequestsShouldCorrect() throws InterruptedException {
        doReturn(new Response(10)).when(server).processRequest(90);
        doReturn(new Response(20)).when(server).processRequest(80);
        doReturn(new Response(30)).when(server).processRequest(70);
        doReturn(new Response(40)).when(server).processRequest(60);

        client.sendRequests();

        verify(server,times(4)).processRequest(anyInt());
    }
}