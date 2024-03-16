package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.domain.exception.EmployeeNotFoundException;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.micrometer.common.util.StringUtils.isBlank;


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
