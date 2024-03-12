package com.sampleddd.employees.presentation.employee;

import java.util.List;

/**
 * 出力のための複数の従業員情報のレスポンスを格納するクラス。
 *
 * @param employees EmployeeResponse のリスト。
 */
public record EmployeeResponses(List<EmployeeResponse> employees) {
}
