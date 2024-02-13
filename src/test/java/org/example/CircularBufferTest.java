package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CircularBufferTest {

    @Test
    void put(){
        CircularBuffer<Integer> cBuff = new CircularBuffer<>(4);
        cBuff.put(10);
        cBuff.put(11);
        cBuff.put(12);
        cBuff.put(13);
        cBuff.put(14);
        cBuff.put(15);

        String expected = "[14, 15, 12, 13]";

        Assertions.assertEquals(expected, cBuff.print());
    }

    @Test
    void get(){
        CircularBuffer<Integer> cBuff = new CircularBuffer<>(4);
        cBuff.put(10);
        cBuff.put(11);
        cBuff.put(12);
        cBuff.put(13);

        Assertions.assertEquals(10, cBuff.get());
        Assertions.assertEquals(11, cBuff.get());
        Assertions.assertEquals(12, cBuff.get());
        Assertions.assertEquals(13, cBuff.get());
        Assertions.assertEquals(10, cBuff.get());
    }

    @Test
    void getNull(){
        CircularBuffer<Integer> cBuff = new CircularBuffer<>(4);
        cBuff.put(10);

        Assertions.assertEquals(10, cBuff.get());
        Assertions.assertNull(cBuff.get());
        Assertions.assertNull(cBuff.get());
    }

    @Test
    void getEmpty(){
        CircularBuffer<Integer> cBuff = new CircularBuffer<>(4);
        Assertions.assertThrows(EmptyBufferException.class, cBuff::get, CircularBuffer.EMPTY_BUFFER);
    }
}
