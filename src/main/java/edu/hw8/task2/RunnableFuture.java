package edu.hw8.task2;

public class RunnableFuture {
    private volatile boolean isDone = false;

    public void setDone() {
        isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }
}
