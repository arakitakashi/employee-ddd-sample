package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeGetAllUsecase {
    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> execute() {
        return employeeRepository.findAll().stream().map(this::convertToDto).toList();
    }

    private EmployeeDto convertToDto(Employee employee) {
        return new EmployeeDto(
            employee.id(),
            employee.firstName(),
            employee.lastName()
        );
    }
}
