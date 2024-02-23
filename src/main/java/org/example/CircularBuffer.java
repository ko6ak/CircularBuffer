package org.example;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Tag(name = "CircularBuffer", description = "Циклический буфер для хранения любых объектов")
public class CircularBuffer<T> {
    public static final String EMPTY_BUFFER = "Buffer is empty!";

    @Schema(description = "Размер буфера", example = "100")
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

    @Operation(summary = "Добавляет элемент в буфер")
    public synchronized void put(T item) {
        if (buffer.size() == n) buffer.set(writeIndex, item);
        else buffer.add(item);
        writeIndex = (writeIndex + 1) % n;
    }

    @Operation(summary = "Забирает элемент из буфера",
            responses = {
                    @ApiResponse(description = "Возвращает элемент", content = @Content(mediaType = "Object")),
                    @ApiResponse(description = "Возвращает ошибку \"Buffer is empty!\" если буфер пуст",
                            content = @Content(mediaType = "EmptyBufferException"))
            }
    )
    public synchronized T get() {
        if (buffer.isEmpty()) throw new EmptyBufferException(EMPTY_BUFFER);
        if (buffer.size() <= readIndex) return null;
        T item = buffer.get(readIndex);
        readIndex = (readIndex + 1) % n;
        return item;
    }

    @Operation(summary = "Выводит содержимое буфера в консоль")
    public String print(){
        return buffer.toString();
    }
}
