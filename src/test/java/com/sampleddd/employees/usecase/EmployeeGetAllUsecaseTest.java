package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeGetAllUsecaseTest {
    @InjectMocks
    EmployeeGetAllUsecase sut;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void 全ての住所情報を返す() {
        // arrange
        when(employeeRepository.findAll()).thenReturn(List.of(
                new Employee(1, "Taro", "Yamada"),
                new Employee(2, "Jiro", "Yamada")
            )
        );

        List<EmployeeDto> expected = List.of(
            new EmployeeDto(1, "Taro", "Yamada"),
            new EmployeeDto(2, "Jiro", "Yamada")
        );

        // act
        List<EmployeeDto> actual = sut.execute();

        // assert
        assertThat(actual).isEqualTo(expected);
    }
}
