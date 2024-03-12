package com.sampleddd.employees.presentation;

import com.sampleddd.employees.presentation.employee.EmployeeResponse;
import com.sampleddd.employees.presentation.employee.EmployeeResponses;
import com.sampleddd.employees.usecase.EmployeeGetAllUsecase;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmployeeController {
    private final EmployeeGetAllUsecase employeeGetAllUsecase;

    /**
     * 全ての従業員情報を取得します。
     *
     * @return 従業員情報のリスト
     */
    @GetMapping("v1/employees")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponses getEmployees() {
        List<EmployeeResponse> employeeResponses =
            employeeGetAllUsecase.execute().stream().map(this::convertDtoToResponse).toList();

        return new EmployeeResponses(employeeResponses);
    }

    private EmployeeResponse convertDtoToResponse(EmployeeDto dto) {
        return new EmployeeResponse(String.valueOf(dto.id()), dto.firstName(), dto.lastName());
    }
}
