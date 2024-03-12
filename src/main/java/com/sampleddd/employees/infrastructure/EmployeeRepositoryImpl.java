package com.sampleddd.employees.infrastructure;

import static com.sampleddd.employees.domain.exception.ExceptionMessages.DATABASE_ACCESS_ERROR_MESSAGE;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * {@link EmployeeRepository}のJDBCによる実装。 従業員情報のデータベース操作を担います。
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> findAll() {
        String query = "SELECT id, first_name, last_name FROM employees";

        try {
            return jdbcTemplate.query(query, new DataClassRowMapper<>(EmployeeRecord.class))
                .stream().map(this::mapToEmployee).toList();
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }
    }

    private Employee mapToEmployee(EmployeeRecord employeeRecord) {
        return new Employee(
            employeeRecord.id(),
            employeeRecord.firstName(),
            employeeRecord.lastName()
        );
    }
}
