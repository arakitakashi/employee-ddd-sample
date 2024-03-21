package com.sampleddd.employees.usecase.employee;

import static io.micrometer.common.util.StringUtils.isBlank;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.domain.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 指定されたIDの従業員情報の更新を行うユースケースクラス。リポジトリを利用して、操作を行います。
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeUpdateUsecase {

    private final EmployeeRepository employeeRepository;

    /**
     * 既存の従業員情報を更新します。
     *
     * @param employeeDto 更新する従業員情報。
     */
    public void execute(EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(String.valueOf(employeeDto.id()))
            .orElseThrow(() -> new EmployeeNotFoundException(String.valueOf(employeeDto.id())));
        Employee employee = dtoToEmployee(existingEmployee, employeeDto);

        employeeRepository.update(employee);
    }

    private Employee dtoToEmployee(Employee existingEmployee, EmployeeDto employeeDto) {
        String updateFirstName =
            !isBlank(employeeDto.firstName()) ? employeeDto.firstName()
                : existingEmployee.firstName();

        String updateLastName =
            !isBlank(employeeDto.lastName()) ? employeeDto.lastName()
                : existingEmployee.lastName();

        return new Employee(existingEmployee.id(), updateFirstName, updateLastName);
    }
}
