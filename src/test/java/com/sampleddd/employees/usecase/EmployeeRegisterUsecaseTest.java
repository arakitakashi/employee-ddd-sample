package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeRegisterUsecaseTest {
    @InjectMocks
    EmployeeRegisterUsecase sut;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void 指定された従業員情報を新規登録する() {
        // arrange
        long expected = 3L;

        // act
        long actual = sut.execute("Hanako", "Shirato");

        // assert
        assertThat(actual).isEqualTo(expected);
    }
}
