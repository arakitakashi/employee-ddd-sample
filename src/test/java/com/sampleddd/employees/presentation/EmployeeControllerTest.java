package com.sampleddd.employees.presentation;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.presentation.employee.EmployeeRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @MockBean
    EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Nested
    class 参照 {
        @Test
        void 全ての従業員情報を取得する() throws Exception {
            // arrange
            when(employeeRepository.findAll()).thenReturn(List.of(
                    new Employee(1L, "Taro", "Yamada"),
                    new Employee(2L, "Jiro", "Yamada")
            ));

            // assert
            given()
                    .when()
                    .get("v1/employees")
                    .then()
                    .statusCode(200)
                    .body("employees.size()", is(2))
                    .body("employees[0].id", is("1"))
                    .body("employees[0].firstName", is("Taro"))
                    .body("employees[0].lastName", is("Yamada"))
                    .body("employees[1].id", is("2"))
                    .body("employees[1].firstName", is("Jiro"))
                    .body("employees[1].lastName", is("Yamada"));
        }

        @Test
        void 指定したIDの従業員情報を取得する() {
            // arrange
            String employeeId = "1";
            when(employeeRepository.findById(employeeId)).thenReturn(
                    Optional.of(new Employee(1L, "Yamada", "Taro"))
            );

            // assert
            given()
                    .when()
                    .get("/v1/employees/{id}", employeeId)
                    .then()
                    .statusCode(200)
                    .assertThat()
                    .body("id", is(employeeId))
                    .body("firstName", is("Yamada"))
                    .body("lastName", is("Taro"));
        }
    }

    @Nested
    class 新規登録 {
        @Test
        void 指定した従業員情報を登録する() throws Exception {
            // arrange
            when(employeeRepository.nextId()).thenReturn(3L);

            // assert
            EmployeeRequest employeeRequest =
                    new EmployeeRequest("Hanako", "Shirato");

            given()
                    .contentType("application/json")
                    .body(employeeRequest)
                    .when()
                    .post("/v1/employees")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Location", containsString("/v1/employees/3"));
        }
    }
}
