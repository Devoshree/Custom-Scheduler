package com.company;

public class Task implements Runnable{
    private String s;

    Task(String s){
        this.s = s;
    }

    @Override
    public void run() {
        System.out.println(s +" started at " + System.currentTimeMillis() / 1000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(s +" ended at " + System.currentTimeMillis() / 1000);

        // Add a callback here or set some signal...
    }
}