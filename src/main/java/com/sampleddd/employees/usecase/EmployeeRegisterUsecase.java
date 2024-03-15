package com.sampleddd.employees.usecase;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRegisterUsecase {
    private final EmployeeRepository employeeRepository;

    public long execute(String firstName, String lastName) {
        long id = employeeRepository.nextId();

        Employee employee = new Employee(id, firstName, lastName);
        employeeRepository.register(employee);

        return id;
    }
}
