package com.hsoft.practice;

import com.hsoft.util.LimitedQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LimitedQueueTest {

    Queue<Double> queue = new LimitedQueue<Double>(5);

    @BeforeEach
    void s() {
        queue.add(1.0);
        queue.add(2.0);
        queue.add(3.0);
        queue.add(4.0);
        queue.add(5.0);
        queue.add(6.0);
        queue.add(7.0);
    }

    @Test
    @DisplayName("LimitedQueue should have a maximum queue size")
    void maxSize() {
        assertEquals(queue.size(), 5);
    }

    @Test
    @DisplayName("LimitedQueue should always remove oldest entry and remain FIFO")
    void shouldMaintainFIFO() {
        assertEquals(queue.peek(), 3.0);
    }
}
