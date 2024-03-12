package com.sampleddd.employees.usecase;

import com.sampleddd.employees.usecase.dto.EmployeeDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmployeeGetAllUsecase {
    public List<EmployeeDto> execute() {
       return List.of(
           new EmployeeDto(1, "Taro", "Yamada"),
           new EmployeeDto(2, "Jiro", "Yamada")
       );
    }
}
