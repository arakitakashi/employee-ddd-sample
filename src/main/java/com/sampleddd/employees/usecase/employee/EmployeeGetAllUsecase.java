package com.sampleddd.employees.usecase.employee;

import com.sampleddd.employees.domain.employee.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 従業員情報の全件取得を行うユースケースクラス。リポジトリを利用して、操作を行います。
 */
@Service
@RequiredArgsConstructor
public class EmployeeGetAllUsecase {

    private final EmployeeRepository employeeRepository;

    /**
     * 全ての従業員情報を取得します。
     *
     * @return 従業員情報 DTO のリスト。
     */
    public List<EmployeeDto> execute() {
        return employeeRepository.findAll().stream().map(EmployeeToDtoConverter::execute).toList();
    }
}
