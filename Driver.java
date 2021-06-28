package com.company;

import java.util.concurrent.TimeUnit;

public class Driver{
    public static void main(String[] args) {
        CustomSchedulerService schedulerService = new CustomSchedulerService(3);
        Runnable task1 = new Task("task1");
        schedulerService.schedule(task1, 1, TimeUnit.SECONDS);
        Runnable task2 = new Task("task2");
        schedulerService.scheduleAtFixedRate(task2,1, 7, TimeUnit.SECONDS);
        Runnable task3 = new Task("task3");
        schedulerService.scheduleWithFixedDelay(task3,2,5,TimeUnit.SECONDS);
        schedulerService.start();
    }
}
