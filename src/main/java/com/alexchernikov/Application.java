package com.alexchernikov;

import com.alexchernikov.designpatterns.strategy.core.Storage;
import com.alexchernikov.designpatterns.strategy.core.StorageType;
import com.alexchernikov.designpatterns.strategy.spring.StorageSelector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner demo(StorageSelector selector) {
        return args -> {

            System.out.println("=== LIFO Demo ===");
            Storage<Integer> lifo = selector.getStorage(StorageType.LIFO);
            lifo.add(1);
            lifo.add(2);
            lifo.add(3);
            System.out.println(lifo.remove()); // 3
            System.out.println(lifo.remove()); // 2
            System.out.println(lifo.remove()); // 1

            System.out.println("\n=== FIFO Demo ===");
            Storage<Integer> fifo = selector.getStorage(StorageType.FIFO);
            fifo.add(1);
            fifo.add(2);
            fifo.add(3);
            System.out.println(fifo.remove()); // 1
            System.out.println(fifo.remove()); // 2
            System.out.println(fifo.remove()); // 3
        };
    }
}
