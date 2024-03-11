package com.sampleddd.employees.domain.employee;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmployeeTest {
    @ParameterizedTest(name = "{3}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # FIRST NAME | LAST NAME | MESSAGE            | TESTNAME
                     |    YAMADA | Illegal first name | 名前がnull
        """)
    void ガード条件に違反する不正な値を検出する(String firstName, String lastName, String message, String testName)
        throws Exception {
        // assert
        String id = "1";
        assertThatThrownBy(() -> new Employee(id, firstName, lastName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(message);
    }
}
