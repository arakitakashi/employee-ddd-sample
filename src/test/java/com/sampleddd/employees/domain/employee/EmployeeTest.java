package com.sampleddd.employees.domain.employee;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmployeeTest {
    @ParameterizedTest(name = "{4}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # ID | FIRST NAME | LAST NAME | MESSAGE                      | TESTNAME
          -1 |            |    YAMADA | Employee ID must be positive | IDがマイナス
           0 |            |    YAMADA | Employee ID must be positive | IDが0 
           1 |            |    YAMADA | First name must not be blank | 名前がnull
           1 |         '' |    YAMADA | First name must not be blank | 名前が空
           1 |       '  ' |    YAMADA | First name must not be blank | 名前がスペースのみ
           1 |       TARO |           | Last name must not be blank  | 名字がnull
           1 |       TARO |        '' | Last name must not be blank  | 名字が空
           1 |       TARO |      '  ' | Last name must not be blank  | 名字がスペースのみ
        """)
    void ガード条件に違反する不正な値を検出する(long id, String firstName, String lastName, String message, String testName)
        throws Exception {
        // assert
        assertThatThrownBy(() -> new Employee(id, firstName, lastName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(message);
    }
}
