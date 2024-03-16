package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.domain.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 従業員情報の削除を行うユースケースクラス。リポジトリを利用して、操作を行います。
 */
@Service
@RequiredArgsConstructor
public class EmployeeDeleteUsecase {
    private final EmployeeRepository employeeRepository;

    /**
     * 従業員情報を削除します。
     *
     * @param id ID。
     */
    public void execute(String id) {
        boolean isSuccess = employeeRepository.delete(id);
        if (!isSuccess) {
            throw new EmployeeNotFoundException(id);
        }
    }
}
