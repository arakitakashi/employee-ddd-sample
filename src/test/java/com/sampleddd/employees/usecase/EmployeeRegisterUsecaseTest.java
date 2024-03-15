package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeRegisterUsecaseTest {
    @InjectMocks
    EmployeeRegisterUsecase sut;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void 指定された従業員情報を新規登録する() {
        // arrange
        when(employeeRepository.nextId()).thenReturn(3L);
        long expected = 3L;

        // act
        long actual = sut.execute("Hanako", "Shirato");

        // assert
        assertThat(actual).isEqualTo(expected);
    }
}
