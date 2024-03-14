package com.sampleddd.employees.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.sampleddd.employees.usecase.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeGetByIdUsecaseTest {
    @Autowired
    EmployeeGetByIdUsecase sut;

    @Test
    void 指定されたIdの住所情報を返す() {
        // arrange
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
}
