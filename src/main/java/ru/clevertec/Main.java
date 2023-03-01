package ru.clevertec;

import ru.clevertec.concurrency.Client;
import ru.clevertec.concurrency.Request;
import ru.clevertec.concurrency.Server;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        List<Client> clients = List.of(
                new Client(server, List.of(new Request(52), new Request(42), new Request(63), new Request(22))),
                new Client(server, List.of(new Request(52), new Request(42), new Request(63), new Request(22))),
                new Client(server, List.of(new Request(52), new Request(42), new Request(63), new Request(22))),
                new Client(server, List.of(new Request(52), new Request(42), new Request(63), new Request(22))),
                new Client(server, List.of(new Request(52), new Request(42), new Request(63), new Request(22)))
        );
        clients.forEach(Client::sendRequests);

        clients.stream().flatMap(client -> client.getResponses().stream())
                .forEach(response -> System.out.println("Value: " + response.value()));
    }
}
