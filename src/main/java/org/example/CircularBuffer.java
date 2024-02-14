package org.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CircularBuffer<T> {
    public static final String EMPTY_BUFFER = "Buffer is empty!";

    private final int n;
    private final List<T> buffer;
    private int writeIndex;
    private int readIndex;

    public CircularBuffer(int n) {
        this.n = n;
        writeIndex = 0;
        readIndex = 0;
        buffer = new CopyOnWriteArrayList<>();
    }

    public synchronized void put(T item) {
        if (buffer.size() == n) buffer.set(writeIndex, item);
        else buffer.add(item);
        writeIndex = (writeIndex + 1) % n;
    }

    public synchronized T get() {
        if (buffer.isEmpty()) throw new EmptyBufferException(EMPTY_BUFFER);
        if (buffer.size() <= readIndex) return null;
        T item = buffer.get(readIndex);
        readIndex = (readIndex + 1) % n;
        return item;
    }

    public String print(){
        return buffer.toString();
    }
}
