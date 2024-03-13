package com.sampleddd.employees.domain.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmployeeTest {
    long MAX_LENGTH_EXCEEDED_ID = 12345678901L;
    long VALID_ID = 1;
    String MAX_LENGTH_EXCEEDED_FIRST_NAME = "A".repeat(101);
    String VALID_FIRST_NAME = "Taro";
    String MAX_LENGTH_EXCEEDED_LAST_NAME = "B".repeat(101);
    String VALID_LAST_NAME = "Yamada";

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
            .hasMessage(message);
    }

    @Test
    void IDの値が10桁以内ではない場合不正を検出する() throws Exception {
        // assert
        assertThatThrownBy(() -> new Employee(MAX_LENGTH_EXCEEDED_ID, VALID_FIRST_NAME, VALID_LAST_NAME))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Employee ID must not exceed the maximum length");
    }

    @Test
    void 名の値が100桁以内ではない場合不正を検出する() throws Exception {
        // assert
        assertThatThrownBy(() -> new Employee(VALID_ID, MAX_LENGTH_EXCEEDED_FIRST_NAME, VALID_LAST_NAME))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("First name must not exceed the maximum length");
    }

    @Test
    void 姓の値が100桁以内ではない場合不正を検出する() throws Exception {
        // assert
        assertThatThrownBy(() -> new Employee(VALID_ID, VALID_FIRST_NAME, MAX_LENGTH_EXCEEDED_LAST_NAME))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Last name must not exceed the maximum length");
    }
}
