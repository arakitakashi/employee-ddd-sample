package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class EmployeeDeleteUsecaseTest {
    @InjectMocks
    EmployeeDeleteUsecase sut;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void 指定したIDの従業員情報を削除する() {
        // arrange
        String employeeId = "1";

        // execute
        sut.execute(employeeId);

        // assert
        verify(employeeRepository, times(1)).delete("1");
    }
}
