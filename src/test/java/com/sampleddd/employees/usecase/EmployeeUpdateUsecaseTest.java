package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.domain.exception.EmployeeNotFoundException;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class EmployeeUpdateUsecaseTest {
    @InjectMocks
    EmployeeUpdateUsecase sut;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void 指定したIDの従業員情報を更新する() {
        // arrange
        EmployeeDto employeeDto = createEmployeeDto(1L);
        Employee employee = new Employee(employeeDto.id(), "Taro", employeeDto.lastName());

        when(employeeRepository.findById(String.valueOf(employeeDto.id()))).thenReturn(Optional.of(employee));

        // act
        sut.execute(employeeDto);

        // assert
        verify(employeeRepository, times(1)).update(employee);
    }

    @Test
    void 指定したIDの住所情報が存在しない場合例外が発生する() {
        // arrange
        EmployeeDto employeeDto = createEmployeeDto(99L);

        // assert;
        assertThatThrownBy(() -> sut.execute(employeeDto))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("specified employee [id = 99] is not found.");
    }

    private EmployeeDto createEmployeeDto(long id) {
        return new EmployeeDto(id, null, "Yamada");
    }
}
