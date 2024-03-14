package com.sampleddd.employees.infrastructure;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.sampleddd.employees.domain.exception.ExceptionMessages.DATABASE_ACCESS_ERROR_MESSAGE;
import static com.sampleddd.employees.domain.exception.ExceptionMessages.FAIL_GET_NEXT_ID_NUMBER_MESSAGE;

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
            return jdbcTemplate.query(query, new DataClassRowMapper<>(EmployeeRecord.class)).stream().map(this::mapToEmployee).toList();
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findById(String id) {
        String query = "SELECT id, first_name, last_name FROM employees WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", Integer.parseInt(id));

        try {
            EmployeeRecord employeeRecord = jdbcTemplate.queryForObject(query, params, new DataClassRowMapper<>(EmployeeRecord.class));
            return Optional.ofNullable(mapToEmployee(employeeRecord));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }
    }

    @Override
    public long nextId() {
        String query = "SELECT NEXTVAL('EMPLOYEE_ID_SEQ')";

        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(query, new HashMap<>(), Long.class)
            ).orElseThrow(() -> new IllegalStateException(FAIL_GET_NEXT_ID_NUMBER_MESSAGE.message()));
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }
    }

    private Employee mapToEmployee(EmployeeRecord employeeRecord) {
        return new Employee(employeeRecord.id(), employeeRecord.firstName(), employeeRecord.lastName());
    }
}
