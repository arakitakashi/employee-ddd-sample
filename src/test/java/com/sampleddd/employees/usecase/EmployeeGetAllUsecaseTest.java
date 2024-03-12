package com.sampleddd.employees.usecase;

import com.sampleddd.employees.usecase.dto.EmployeeDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeGetAllUsecaseTest {
    @Autowired
    EmployeeGetAllUsecase sut;

    @Test
    void 全ての住所情報を返す() {
        // arrange
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
