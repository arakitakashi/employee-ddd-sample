package com.sampleddd.employees.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.sampleddd.employees.domain.employee.Employee;
import java.sql.DriverManager;
import java.util.List;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false)
public class EmployeeRepositoryImplTest {
    private static final String DB_URL =
        "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "utuser";
    private static final String DB_PASSWORD = "utpassword";
    private static final ConnectionHolder connectionHolder =
        () -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

    @Autowired
    EmployeeRepository sut;

    @BeforeAll
    static void setUpAll() {
        Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASSWORD).load().migrate();
    }

    @Nested
    class 全件取得 {
        @Test
        @DataSet(value = "datasets/employee/employees-setup.yml")
        void 全ての従業員情報を取得する() {
            // arrange
            Employee employee1 = new Employee(1, "Taro", "Yamada");
            Employee employee2 = new Employee(2, "Jiro", "Yamada");
            List<Employee> expected = List.of(employee1, employee2);

            // act
            List<Employee> actual = sut.findAll();

            // assert
            assertThat(actual).isEqualTo(expected);
        }
    }
}