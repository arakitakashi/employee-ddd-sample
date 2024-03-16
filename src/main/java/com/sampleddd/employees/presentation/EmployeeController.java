package com.sampleddd.employees.presentation;

import com.sampleddd.employees.presentation.employee.EmployeeRequest;
import com.sampleddd.employees.presentation.employee.EmployeeResponse;
import com.sampleddd.employees.presentation.employee.EmployeeResponses;
import com.sampleddd.employees.usecase.EmployeeGetAllUsecase;
import com.sampleddd.employees.usecase.EmployeeGetByIdUsecase;
import com.sampleddd.employees.usecase.EmployeeRegisterUsecase;
import com.sampleddd.employees.usecase.EmployeeUpdateUsecase;
import com.sampleddd.employees.usecase.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class EmployeeController {
    private final EmployeeGetAllUsecase employeeGetAllUsecase;
    private final EmployeeGetByIdUsecase employeeGetByIdUsecase;
    private final EmployeeRegisterUsecase employeeRegisterUsecase;
    private final EmployeeUpdateUsecase employeeUpdateUsecase;

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

    /**
     * 指定されたIDの従業員情報を取得します。存在しない場合は404 Not Foundを返します。
     *
     * @param id ID。
     * @return 従業員情報
     */
    @GetMapping("v1/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse getEmployeeById(@PathVariable String id) {
        EmployeeDto employeeDto = employeeGetByIdUsecase.execute(id);
        return convertDtoToResponse(employeeDto);
    }

    /**
     * 新しい従業員情報を登録します。登録に成功すると201 Created とともに従業員情報のURIを返します。
     *
     * @param employeeRequest 新規従業員情報。
     * @return 登録された従業員情報のURIを含むレスポンスエンティティ
     */
    @PostMapping("/v1/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        long createdEmployeeId = employeeRegisterUsecase.execute(employeeRequest.firstName(),
            employeeRequest.lastName());
        URI location = getLocation(createdEmployeeId);
        return ResponseEntity.created(location).build();
    }

    private URI getLocation(long createdEmployeeId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
            .buildAndExpand(createdEmployeeId).toUri();
    }

    /**
     * 指定されたIDの従業員情報を更新します。 更新に成功すると204 No Contentを返します。
     *
     * @param id              ID。
     * @param employeeRequest 更新する従業員情報。
     * @return レスポンスエンティティ。
     */
    @PatchMapping("/v1/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateEmployee(
        @PathVariable String id,
        @RequestBody EmployeeRequest employeeRequest
    ) {
        employeeUpdateUsecase.execute(
            new EmployeeDto(Long.parseLong(id), employeeRequest.firstName(),
                employeeRequest.lastName()));
        return ResponseEntity.noContent().build();
    }
}
