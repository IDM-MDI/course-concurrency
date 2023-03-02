package ru.clevertec.concurrency;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Client {
    private final Server server;
    private List<Request> requests;
    private final List<Response> responses;
    public Client(Server server, List<Request> requests) {
        this.server = server;
        this.requests = requests;
        this.responses = new ArrayList<>();
    }
    public void sendRequests() {
        ExecutorService service = Executors.newFixedThreadPool(requests.size());
        List<Future<Response>> futures = new ArrayList<>();
        requests.forEach(request -> futures.add(service.submit(() -> server.processRequest(request.value()))));
        futures.forEach(this::addToResponse);
        service.shutdown();
    }

    @SneakyThrows
    private void addToResponse(Future<Response> responseFuture) {
        responses.add(responseFuture.get());
    }

    public List<Response> getResponses() {
        return responses;
    }
}
