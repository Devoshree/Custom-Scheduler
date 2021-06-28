package com.company;

import java.util.concurrent.TimeUnit;

public class ScheduledTask {
    private final Runnable task;
    private long executionTime;
    private final int taskType;
    private final long period;
    private final TimeUnit unit;

    public ScheduledTask(Runnable task, long executionTime, int taskType, long period, TimeUnit unit) {
        this.task = task;
        this.executionTime = executionTime;
        this.taskType = taskType;
        this.period = period;
        this.unit = unit;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public int getTaskType() {
        return taskType;
    }

    public Long getPeriod() {
        return period;
    }

    public Runnable getTask() {
        return task;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public TimeUnit getUnit() {
        return unit;
    }
}
