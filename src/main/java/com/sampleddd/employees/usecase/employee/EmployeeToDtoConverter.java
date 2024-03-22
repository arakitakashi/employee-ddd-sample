package com.sampleddd.employees.usecase.employee;

import com.sampleddd.employees.domain.employee.Employee;

/**
 * Employee オブジェクトを EmployeeDto に変換するクラス。
 */
final class EmployeeToDtoConverter {

    private EmployeeToDtoConverter() {
    }

    /**
     * Employee オブジェクトを EmployeeDto に変換して返します。
     *
     * @param employee 従業員オブジェクト。
     * @return 従業員情報の DTO。
     */
    public static EmployeeDto execute(Employee employee) {
        return new EmployeeDto(employee.id(), employee.firstName(), employee.lastName());
    }
}
