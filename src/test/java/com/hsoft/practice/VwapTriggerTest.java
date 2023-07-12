package com.hsoft.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * One can add own unit tests here and/or in another class
 */
public class VwapTriggerTest {

  private final VwapTrigger vwapTrigger = new VwapTrigger();

  @Test
  @DisplayName("computeVwap() should compute correct Vwap")
  void shouldComputeCorrectVwap() {
    Queue<Transaction> queue = new LinkedList<>();
    queue.add(new Transaction(1000,100.0));
    queue.add(new Transaction(2000,101.0));
    queue.add(new Transaction(3000,102.0));
    assertEquals(vwapTrigger.computeVwap(queue), 101.33333333333333);
  }

}
