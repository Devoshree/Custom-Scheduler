package com.company;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class CustomSchedulerService  {
    private final PriorityQueue<ScheduledTask> taskQueue;
    private final CustomThreadPool customThreadPool;

    public CustomSchedulerService(int threadPoolSize) {
        this.taskQueue = new PriorityQueue<>(Comparator.comparingLong(ScheduledTask::getExecutionTime)); // Sorting on the basis of execution time
        this.customThreadPool = new CustomThreadPool(threadPoolSize);
    }

    /**
     * Creates and executes a one-shot action that becomes enabled after the given delay.
     */
    public void schedule(Runnable task, long initialDelay, TimeUnit time) {
        System.out.println("Current Time: " + System.currentTimeMillis() / 1000);
        long currTime = System.currentTimeMillis();
        if (task == null) {
            System.out.println("Task is null");
        } else if (initialDelay < 0) {
            System.out.println("Non-positive delay not possible");
        } else {
            taskQueue.offer(new ScheduledTask(task, currTime + time.toMillis(initialDelay), 1, 0L, TimeUnit.SECONDS));
        }
    }

    /**
     * Creates and executes a periodic action that becomes enabled first after the given initial delay, and
     * subsequently with the given period; that is executions will commence after initialDelay then
     * initialDelay+period, then initialDelay + 2 * period, and so on.
     */
    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit time) {
        long currTime = System.currentTimeMillis();
        if (task == null) {
            System.out.println("Task is null");
        } else if (initialDelay < 0) {
            System.out.println("Non-positive delay not possible");
        } else if (period == 0) {
            System.out.println("Task should be scheduled once as period is 0");
            schedule(task, initialDelay, time);
        } else {
            taskQueue.offer(new ScheduledTask(task, currTime + time.toMillis(initialDelay), 2, period, time));
        }
    }

    /*
     * Creates and executes a periodic action that becomes enabled first after the given initial delay, and
     * subsequently with the given delay between the termination of one execution and the commencement of the next.
     */
    public void scheduleWithFixedDelay(Runnable task, long initialDelay, long period, TimeUnit time) {
        long currTime = System.currentTimeMillis();
        if (task == null) {
            System.out.println("Task is null");
        } else if (initialDelay < 0) {
            System.out.println("Non-positive delay not possible");
        } else if (period == 0) {
            System.out.println("Task should be scheduled once as period is 0");
            schedule(task, initialDelay, time);
        } else {
            taskQueue.offer(new ScheduledTask(task, currTime + time.toMillis(initialDelay), 3, period, time));
        }
    }

    public void start() {
        while (true) {
            if (!taskQueue.isEmpty() && System.currentTimeMillis() >= taskQueue.peek().getExecutionTime()) {
                ScheduledTask task = taskQueue.poll();
                long newScheduledTime = 0;
                switch (task.getTaskType()) {
                    case 1:
                        customThreadPool.execute(task);
                        break;
                    case 2:
                        newScheduledTime = task.getExecutionTime() + task.getUnit().toMillis(task.getPeriod());
                        customThreadPool.execute(task);
                        task.setExecutionTime(newScheduledTime);
                        taskQueue.add(task);
                        break;
                    case 3:
                        customThreadPool.execute(task);
                        //wait for task to finish.... "Here we need a callback or some signal to move frwrd"
                        newScheduledTime = System.currentTimeMillis() + task.getUnit().toMillis(task.getPeriod());
                        task.setExecutionTime(newScheduledTime);
                        taskQueue.add(task);
                        break;
                }
            }
        }
    }
}
