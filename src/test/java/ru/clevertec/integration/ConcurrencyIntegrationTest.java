package ru.clevertec.integration;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.concurrency.Client;
import ru.clevertec.concurrency.Request;
import ru.clevertec.concurrency.Response;
import ru.clevertec.concurrency.Server;

import java.util.List;

class ConcurrencyIntegrationTest {
    List<Client> clients;
    Server server;
    @BeforeEach
    void setup() {
        List<List<Request>> requestList = List.of(
                List.of(new Request(10),new Request(50),new Request(90),new Request(30)),
                List.of(new Request(20),new Request(60),new Request(100),new Request(40)),
                List.of(new Request(30),new Request(70),new Request(10),new Request(50)),
                List.of(new Request(40),new Request(80),new Request(20),new Request(60))
        );
        server = new Server();
        clients = List.of(
                new Client(server,requestList.get(0)),
                new Client(server,requestList.get(1)),
                new Client(server,requestList.get(2)),
                new Client(server,requestList.get(3))
        );
    }

    @Test
    void serverShouldProcessClients() {
        List<Response> expected = List.of(
                new Response(90),new Response(80), new Response(70), new Response(60),
                new Response(50),new Response(40), new Response(30), new Response(20),
                new Response(10),new Response(0), new Response(90), new Response(80),
                new Response(70),new Response(60), new Response(50), new Response(40)
        );
        clients.forEach(Client::sendRequests);
        List<Response> actual = clients.stream()
                .flatMap(client -> client.getResponses().stream())
                .toList();
        Assertions.assertThat(actual).containsAll(expected);
    }

    @Test
    void serverShouldProcessClientsWrong() {
        List<Response> expected = List.of(
                new Response(9110),new Response(81110), new Response(71110), new Response(61110),
                new Response(5110),new Response(41110), new Response(31110), new Response(21110),
                new Response(1110),new Response(1110), new Response(91110), new Response(8110),
                new Response(7110),new Response(6110), new Response(51110), new Response(4110)
        );
        clients.forEach(Client::sendRequests);
        List<Response> actual = clients.stream()
                .flatMap(client -> client.getResponses().stream())
                .toList();
        Assertions.assertThat(actual).doesNotContainAnyElementsOf(expected);
    }
}
