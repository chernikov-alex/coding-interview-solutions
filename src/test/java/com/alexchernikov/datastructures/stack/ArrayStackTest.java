package com.alexchernikov.datastructures.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayStackTest {
    private Stack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new ArrayStack<>(10);
    }

    @Test
    void pushAndPop_singleElement() {
        stack.push(10);
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void pushAndPop_multipleElements_lifoOrder() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void peek_doesNotRemoveElement() {
        stack.push(5);
        assertEquals(5, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    void pop_onEmptyStack_throwsException() {
        assertThrows(IllegalStateException.class, () -> stack.pop());
    }

    @Test
    void peek_onEmptyStack_throwsException() {
        assertThrows(IllegalStateException.class, () -> stack.peek());
    }


    @Test
    void stack_growsBeyondInitialCapacity() {
        Stack<Integer> stack = new ArrayStack<>(2);

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.size());
        assertEquals(3, stack.pop());
    }
}
