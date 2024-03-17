package com.sampleddd.employees.presentation.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 出力のための従業員情報のレスポンスボディをマッピングするクラス。
 *
 * @param id        従業員ID
 * @param firstName 名
 * @param lastName  姓
 */
public record EmployeeResponse(
    @JsonProperty("id") String id,
    @JsonProperty("firstName") String firstName,
    @JsonProperty("lastName") String lastName
) {
}
