package com.alexchernikov.designpatterns.strategy.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.alexchernikov.designpatterns.strategy.spring")
public class AppConfig {

    @Bean
    public LifoStorage<?> lifoStorage() {
        return new LifoStorage<>();
    }

    @Bean
    public FifoStorage<?> fifoStorage() {
        return new FifoStorage<>();
    }
}
