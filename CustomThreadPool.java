package com.company;

import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {
    // Thread pool size
    private final int poolSize;

    // Internally pool is an array
    private final WorkerThread[] workers;

    private final LinkedBlockingQueue<Runnable> queue;

    public CustomThreadPool(int poolSize) {
        this.poolSize = poolSize;
        queue = new LinkedBlockingQueue<>();
        workers = new WorkerThread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void execute(ScheduledTask task) {
        synchronized (queue) {
            queue.add(task.getTask());
            queue.notify();
        }
    }

    private class WorkerThread extends Thread {
        public void run() {
            Runnable task;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = queue.poll();
                }
                task.run();
            }
        }
    }

    public void shutdown() {
        System.out.println("Shutting down thread pool");
        for (int i = 0; i < poolSize; i++) {
            workers[i] = null;
        }
    }
}
