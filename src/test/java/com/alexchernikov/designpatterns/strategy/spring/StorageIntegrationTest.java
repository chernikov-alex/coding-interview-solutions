package com.alexchernikov.designpatterns.strategy.spring;

import com.alexchernikov.designpatterns.strategy.core.Storage;
import com.alexchernikov.designpatterns.strategy.core.StorageType;
import com.alexchernikov.designpatterns.strategy.core.TestStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class StorageIntegrationTest {

    private Storage<Integer> lifo;
    private Storage<Integer> fifo;

    @BeforeEach
    void setUp() {
        lifo = selector.getStorage(StorageType.LIFO);
        fifo = selector.getStorage(StorageType.FIFO);
    }

    @Autowired
    private StorageSelector selector;

    @Test
    void contextLoads() {
        assertNotNull(lifo);
        assertNotNull(fifo);
    }

    @Test
    @TestStorage(StorageType.LIFO)
    void testLifoStorage() {
        Storage<Integer> lifo = selector.getStorage(StorageType.LIFO);
        lifo.add(1);
        lifo.add(2);
        assertEquals(2, lifo.remove());
        assertEquals(1, lifo.remove());
        assertEquals(0, lifo.size());
    }

    @Test
    void testFifoStorage() {
        Storage<Integer> fifo = selector.getStorage(StorageType.FIFO);
        fifo.add(1);
        fifo.add(2);
        assertEquals(1, fifo.remove());
        assertEquals(2, fifo.remove());
        assertEquals(0, fifo.size());
    }
}
