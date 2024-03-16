package com.sampleddd.employees.domain.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeGlobalExceptionHandler {
    private static final String KEY_OF_CODE = "code";
    private static final String KEY_OF_MESSAGE = "message";
    private static final String KEY_OF_DETAILS = "details";

    /**
     * {@link EmployeeNotFoundException} が発生した場合の処理を行います。 クライアントにはBadRequestと共にエラー情報を返します。
     *
     * @param e 発生した{@link EmployeeNotFoundException}
     * @return エラー情報を含むレスポンスエンティティ
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEmployeeNotFoundException(
        EmployeeNotFoundException e
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0003");
        body.put(KEY_OF_MESSAGE, e.getMessage());
        body.put(KEY_OF_DETAILS, new ArrayList<>());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * 入力値が不正の場合の例外{@link IllegalArgumentException} を処理します。バリデーションエラーの詳細をクライアントに返します。
     *
     * @param e 発生した {@link IllegalArgumentException}
     * @return バリデーションエラー情報を含むレスポンスエンティティ
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
        IllegalArgumentException e
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0002");
        body.put(KEY_OF_MESSAGE, "request validation error is occurred.");
        List<String> details = List.of(e.getMessage());
        body.put(KEY_OF_DETAILS, details);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
