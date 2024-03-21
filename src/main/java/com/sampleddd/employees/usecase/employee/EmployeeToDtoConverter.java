package com.sampleddd.employees.usecase.employee;

import com.sampleddd.employees.domain.employee.Employee;

/**
 * EmployeeオブジェクトをEmployeeDtoに変換するクラス。
 */
final class EmployeeToDtoConverter {

    private EmployeeToDtoConverter() {
    }

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
