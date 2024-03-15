package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeUpdateUsecaseTest {
    @InjectMocks
    EmployeeUpdateUsecase sut;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void 指定したIDの従業員情報を更新する() {
        // arrange
        EmployeeDto employeeDto = new EmployeeDto(1L, null, "Yamada");
        Employee employee = new Employee(employeeDto.id(), "Taro", employeeDto.lastName());

        when(employeeRepository.findById(String.valueOf(employeeDto.id()))).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).update(employee);

        // act
        sut.execute(employeeDto);

        // assert
        verify(employeeRepository, times(1)).update(employee);
    }
}
