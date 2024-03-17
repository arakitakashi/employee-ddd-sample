package com.sampleddd.employees;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class EmployeesApplicationTests {
    @Autowired
    private ApplicationContext context;

    @Test
    void コンテキストがロードされている() {
        assertThat(context).isNotNull();
    }
}
