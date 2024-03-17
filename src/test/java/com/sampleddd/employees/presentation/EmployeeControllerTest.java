package com.sampleddd.employees.presentation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.domain.exception.EmployeeGlobalExceptionHandler;
import com.sampleddd.employees.presentation.employee.EmployeeRequest;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.LoggingEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @Mock
    Appender<ILoggingEvent> appender;

    @Captor
    ArgumentCaptor<ILoggingEvent> captor;

    @MockBean
    EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Nested
    class ルート {
        @Test
        void ルートURLにアクセスができる() {
            // assert
            given()
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class 参照 {
        @Test
        void 全ての従業員情報を取得する() throws Exception {
            // arrange
            when(employeeRepository.findAll()).thenReturn(
                List.of(new Employee(1L, "Taro", "Yamada"), new Employee(2L, "Jiro", "Yamada")));

            // assert
            given().when().get("v1/employees").then().statusCode(200)
                .body("employees.size()", is(2)).body("employees[0].id", is("1"))
                .body("employees[0].firstName", is("Taro"))
                .body("employees[0].lastName", is("Yamada")).body("employees[1].id", is("2"))
                .body("employees[1].firstName", is("Jiro"))
                .body("employees[1].lastName", is("Yamada"));
        }

        @Test
        void 指定したIDの従業員情報を取得する() {
            // arrange
            String employeeId = "1";
            when(employeeRepository.findById(employeeId)).thenReturn(
                Optional.of(new Employee(1L, "Yamada", "Taro")));

            // assert
            given().when().get("/v1/employees/{id}", employeeId).then().statusCode(200).assertThat()
                .body("id", is(employeeId)).body("firstName", is("Yamada"))
                .body("lastName", is("Taro"));
        }

        @Test
        void 指定したIDの従業員情報が存在しない場合400コードを返す() {
            // arrange
            String invalidId = "99";

            // assert
            given()
                .when()
                .get("/v1/employees/{id}", invalidId)
                .then()
                .statusCode(400)
                .assertThat()
                .body("code", equalTo("0003"))
                .body("message", equalTo("specified employee [id = 99] is not found."))
                .body("details", empty());
        }
    }

    @Nested
    class 新規登録 {
        @Test
        void 指定した従業員情報を登録する() throws Exception {
            // arrange
            when(employeeRepository.nextId()).thenReturn(3L);

            // assert
            EmployeeRequest employeeRequest = new EmployeeRequest("Hanako", "Shirato");

            given().contentType("application/json").body(employeeRequest).when()
                .post("/v1/employees").then().statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/v1/employees/3"));
        }

        @ParameterizedTest(name = "{3}の場合")
        @CsvSource(delimiter = '|', textBlock = """
            # FIRST NAME | LAST NAME | MESSAGE                     | TESTNAME
                      '' |    YAMADA | firstName must not be blank | 名前が空文字
                         |    YAMADA | firstName must not be blank | 名前がnull
                    TARO |           | lastName must not be blank  | 名字が空文字
                    TARO |        '' | lastName must not be blank  | 名字がnull
            """)
        void 指定した従業員情報が不正の場合エラーを返す(
            String firstName,
            String lastName,
            String message,
            String testName
        ) {
            // arrange
            EmployeeRequest employeeRequest = new EmployeeRequest(firstName, lastName);
            when(employeeRepository.nextId()).thenReturn(3L);

            // assert
            given()
                .contentType("application/json")
                .body(employeeRequest)
                .when()
                .post("/v1/employees")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("details[0]", is(message));
        }
    }

    @Nested
    class 更新 {
        @Test
        void 指定したIDの従業員情報を更新する() throws Exception {
            EmployeeRequest employeeRequest = new EmployeeRequest(
                null,
                "Yamada"
            );

            when(employeeRepository.findById("1")).thenReturn(
                Optional.of(new Employee(1L, "Taro", "Yamada"))
            );

            given()
                .contentType("application/json")
                .body(employeeRequest)
                .patch("/v1/employees/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }
    }

    @Nested
    class 削除 {
        @Test
        void 指定したIDの従業員情報を削除する() {
            // arrange
            when(employeeRepository.delete("1")).thenReturn(true);
            // assert
            given().delete("/v1/employees/{id}", "1").then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }
    }

    @Nested
    class 共通エラー {
        @Test
        void データベース接続に問題が生じた場合InternalServerErrorのレスポンスを返す() throws Exception {
            // arrange
            when(employeeRepository.findAll())
                .thenThrow(DataAccessResourceFailureException.class);

            // assert
            given()
                .when()
                .get("/v1/employees")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body("code", is("0001"))
                .body("message", is("data access error occurred."));
        }

        @Test
        void 予期しない例外が発生した場合InternalServerErrorのレスポンスを返す() throws Exception {
            // setup
            when(employeeRepository.findAll())
                .thenThrow(NullPointerException.class);

            // assert
            given()
                .when()
                .get("v1/employees")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body("code", is("9999"))
                .body("message", is("system error has occurred."));
        }

        @Test
        void 予期しない例外が発生した場合ログを出力する() throws Exception {
            // setup
            MockitoAnnotations.openMocks(this);
            LoggerContext loggerContext = getLoggerContext();

            Mockito.when(employeeRepository.findAll())
                .thenThrow(NullPointerException.class);

            // assert
            given()
                .when()
                .get("v1/addresses")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            assertLogMessage();

            loggerContext.getLogger(EmployeeGlobalExceptionHandler.class)
                .detachAppender(appender);
        }

        @NotNull
        private LoggerContext getLoggerContext() {
            Logger logger = LoggerFactory.getLogger(EmployeeGlobalExceptionHandler.class);
            appender = mock(Appender.class);

            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.getLogger(EmployeeGlobalExceptionHandler.class).addAppender(appender);
            loggerContext.getLogger(EmployeeGlobalExceptionHandler.class).setAdditive(false);
            return loggerContext;
        }

        private void assertLogMessage() {
            verify(appender).doAppend(captor.capture());
            assertThat(captor.getValue().getLevel()).hasToString("ERROR");
            assertThat(captor.getValue().getMessage()).contains(
                "an unexpected error has occurred.");
        }
    }
}
