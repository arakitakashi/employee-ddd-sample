package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 指定された従業員情報の新規登録を行うユースケースクラス。リポジトリを利用して、操作を行います。
 */
@Service
@RequiredArgsConstructor
public class EmployeeRegisterUsecase {
    private final EmployeeRepository employeeRepository;

    /**
     * 新しい従業員情報を登録します。新規登録をする従業員情報のIDを返します。
     *
     * @param firstName 名。
     * @param lastName  姓。
     * @return 新規登録する従業員ID。
     */
    public long execute(String firstName, String lastName) {
        long id = employeeRepository.nextId();

        Employee employee = new Employee(id, firstName, lastName);
        employeeRepository.register(employee);

        return id;
    }
}
