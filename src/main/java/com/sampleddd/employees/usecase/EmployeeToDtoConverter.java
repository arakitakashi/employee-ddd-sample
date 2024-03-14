package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.usecase.dto.EmployeeDto;

/**
 * EmployeeオブジェクトをEmployeeDtoに変換するクラス。
 */
public class EmployeeToDtoConverter {
    /**
     * EmployeeオブジェクトをEmployeeDtoに変換して返します。
     *
     * @param employee 従業員オブジェクト。
     * @return 従業員DTO。
     */
    public static EmployeeDto execute(Employee employee) {
        return new EmployeeDto(employee.id(), employee.firstName(), employee.lastName());
    }
}
