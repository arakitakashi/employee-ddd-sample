package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 指定されたIDの従業員情報を取得をするユースケースクラス。リポジトリを利用して、操作を行います。
 */
@Service
@RequiredArgsConstructor
public class EmployeeGetByIdUsecase {
    private final EmployeeRepository employeeRepository;

    /**
     * 指定されたIDの従業員情報を取得します。従業員情報が存在しない場合例外を発生させます。
     *
     * @param id ID。
     * @return 指定されたIDの従業員情報を含むDTO。
     */
    public EmployeeDto execute(String id) {
        return employeeRepository.findById(id).map(EmployeeToDtoConverter::execute)
                .orElseThrow();
    }
}
