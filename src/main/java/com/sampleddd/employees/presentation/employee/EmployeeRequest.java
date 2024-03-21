package com.sampleddd.employees.presentation.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 入力された従業員情報のリクエストボディをマッピングするクラス。
 *
 * @param firstName 名。
 * @param lastName  姓。
 */
public record EmployeeRequest(
    @JsonProperty("firstName") String firstName,
    @JsonProperty("lastName") String lastName
) {

}
