package org.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CircularBuffer<T> {
    public static final String EMPTY_BUFFER = "Buffer is empty!";

    private final int n;
    private final List<T> buffer;
    private final AtomicInteger writeIndex;
    private final AtomicInteger readIndex;

    public CircularBuffer(int n) {
        this.n = n;
        writeIndex = new AtomicInteger(0);
        readIndex = new AtomicInteger(0);
        buffer = new CopyOnWriteArrayList<>();
    }

    public void put(T item) {
        if (buffer.size() == n) buffer.set(writeIndex.get(), item);
        else buffer.add(item);
        writeIndex.set(writeIndex.incrementAndGet() % n);
    }

    public T get() {
        if (buffer.isEmpty()) throw new EmptyBufferException(EMPTY_BUFFER);
        if (buffer.size() <= readIndex.get()) return null;
        T item = buffer.get(readIndex.get());
        readIndex.set(readIndex.incrementAndGet() % n);
        return item;
    }

    public String print(){
        return buffer.toString();
    }
}
