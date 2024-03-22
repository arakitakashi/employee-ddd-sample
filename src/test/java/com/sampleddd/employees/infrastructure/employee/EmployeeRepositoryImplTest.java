package com.sampleddd.employees.infrastructure.employee;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

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

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    @Nested
    class 単体取得 {

        @Test
        @DataSet(value = "datasets/employee/employees-setup.yml")
        void 指定されたIdの従業員情報を取得する() {
            // arrange
            Optional<Employee> expected = Optional.of(
                new Employee(1L, "Taro", "Yamada")
            );

            // act
            Optional<Employee> actual = sut.findById("1");

            // assert
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class 新規登録 {

        @Test
        @DataSet(value = "datasets/employee/employees-setup.yml")
        void 指定した従業員情報を新規登録する() {
            // arrange
            Employee newEmployee = createEmployeeForRegister();
            Optional<Employee> expected = Optional.of(createEmployeeForRegister());

            // act
            sut.register(newEmployee);

            // assert
            Optional<Employee> actual = sut.findById("3");
            assertThat(actual).isEqualTo(expected);
        }

        private Employee createEmployeeForRegister() {
            return new Employee(3L, "Hanako", "Shirato");
        }
    }

    @Nested
    class 更新 {

        @Test
        @DataSet(value = "datasets/employee/employees-setup.yml")
        void 指定した従業員情報を更新する() {
            // arrange
            Optional<Employee> expected =
                Optional.of(
                    createEmployeeForUpdate(1L)
                );
            Employee employee = createEmployeeForUpdate(1L);

            // act
            sut.update(employee);

            // assert
            Optional<Employee> actual = sut.findById("1");
            assertThat(actual).isEqualTo(expected);
        }

        private Employee createEmployeeForUpdate(long id) {
            return new Employee(
                id,
                "Taro",
                "Suzuki"
            );
        }
    }

    @Nested
    class 削除 {

        @Test
        @DataSet(value = "datasets/employee/employees-setup.yml")
        void 指定したIDの従業員情報の削除に成功するとtrueを返す() {
            // arrange
            String employeeId = "2";

            // act
            boolean actual = sut.delete(employeeId);

            // assert
            assertThat(actual).isTrue();
        }

        @Test
        @DataSet(value = "datasets/employee/employees-setup.yml")
        void 指定したIDの従業員情報が存在しない場合falseを返す() {
            // arrange
            String employeeId = "99";

            // act
            boolean actual = sut.delete(employeeId);

            // assert
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class ID採番 {

        @Test
        @DataSet(value = "datasets/employee/employees-setup.yml")
        void 新規でIDのためのナンバーを採番する() {
            // arrange
            long expected = 1L;

            resetSequence();

            // act
            long actual = sut.nextId();

            // assert
            assertThat(actual).isEqualTo(expected);
        }

        private void resetSequence() {
            jdbcTemplate.execute("ALTER SEQUENCE EMPLOYEE_ID_SEQ RESTART WITH 1");
        }
    }
}
