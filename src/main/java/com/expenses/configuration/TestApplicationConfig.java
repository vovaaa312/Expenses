package com.expenses.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.expenses")
public class TestApplicationConfig {
    // Конфигурация для тестов без MongoDB
}