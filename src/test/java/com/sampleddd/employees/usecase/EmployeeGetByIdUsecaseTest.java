package com.sampleddd.employees.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.domain.exception.EmployeeNotFoundException;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeGetByIdUsecaseTest {
    @InjectMocks
    EmployeeGetByIdUsecase sut;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void 指定されたIdの従業員情報を返す() {
        // arrange
        when(employeeRepository.findById("1")).thenReturn(
            Optional.of(new Employee(1, "Yamada", "Taro"))
        );
        EmployeeDto expected = new EmployeeDto(
            1,
            "Yamada",
            "Taro"
        );

        // act
        EmployeeDto actual = sut.execute("1");

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 指定されたIdの従業員情報が存在しない場合例外が発生する() {
        // assert
        assertThatThrownBy(() -> sut.execute("99"))
            .isInstanceOf(EmployeeNotFoundException.class)
            .hasMessage("specified employee [id = 99] is not found.");
    }
}
