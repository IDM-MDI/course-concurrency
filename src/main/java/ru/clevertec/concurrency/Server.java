package ru.clevertec.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class Server {
    private static final int HUNDRED = 100;
    private final ReentrantLock lock;

    public Server() {
        lock = new ReentrantLock();
    }

    public Response processRequest(int request) throws InterruptedException {
        lock.lock();
        Thread.sleep((long) (Math.random() * 2000));
        lock.unlock();

        return new Response(HUNDRED - request);
    }
}
