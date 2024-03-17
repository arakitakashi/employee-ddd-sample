package com.sampleddd.employees.usecase.dto;

/**
 * 従業員情報のDTOクラス。
 *
 * @param id        ID。
 * @param firstName 名。
 * @param lastName  姓。
 */
public record EmployeeDto(long id, String firstName, String lastName) {
}
