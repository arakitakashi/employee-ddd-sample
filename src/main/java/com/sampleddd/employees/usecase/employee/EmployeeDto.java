package com.sampleddd.employees.usecase.employee;

/**
 * 従業員情報の DTO クラス。
 *
 * @param id        ID。
 * @param firstName 名。
 * @param lastName  姓。
 */
public record EmployeeDto(long id, String firstName, String lastName) {

}
