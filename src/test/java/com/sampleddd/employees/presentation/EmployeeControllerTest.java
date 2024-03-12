package com.sampleddd.employees.presentation;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
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
            given()
                .when()
                .get("v1/employees")
                .then()
                .statusCode(200)
                .body("employees.size()", is(2))
                .body("employees[0].id", is(1))
                .body("employees[0].firstName", is("Taro"))
                .body("employees[0].lastName", is("Yamada"))
                .body("employees[1].id", is(1))
                .body("employees[1].firstName", is("Jiro"))
                .body("employees[1].lastName", is("Yamada"));
        }
    }
}
