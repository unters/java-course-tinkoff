package edu.hw8.task2;

/* As far as I understand, ThreadPool is just a ThreadPoolExecutor.  */
public interface ThreadPool extends AutoCloseable {
    RunnableFuture execute(Runnable runnable);
}
