package com.sampleddd.employees.infrastructure.employee;

import static com.sampleddd.employees.domain.exception.ExceptionMessages.DATABASE_ACCESS_ERROR_MESSAGE;
import static com.sampleddd.employees.domain.exception.ExceptionMessages.EMPLOYEE_NOT_FOUND_MESSAGE;
import static com.sampleddd.employees.domain.exception.ExceptionMessages.FAIL_GET_NEXT_ID_NUMBER_MESSAGE;

import com.sampleddd.employees.domain.employee.Employee;
import com.sampleddd.employees.domain.employee.EmployeeRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findById(String id) {
        String query = "SELECT id, first_name, last_name FROM employees WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        try {
            EmployeeRecord employeeRecord = jdbcTemplate.queryForObject(query, params,
                new DataClassRowMapper<>(EmployeeRecord.class));
            return Optional.ofNullable(mapToEmployee(employeeRecord));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Employee employee) {
        String query = """
            INSERT INTO employees (id, first_name, last_name)
            VALUES (:id, :firstName, :lastName) 
             """;

        Map<String, Object> params = createParams(employee);

        try {
            jdbcTemplate.update(query, params);
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> update(Employee employee) {
        String query = """
            UPDATE employees
            SET
            first_name = :firstName,
            last_name = :lastName
            WHERE id = :id
            """;

        Map<String, Object> params = createParams(employee);

        try {
            int affectedRows = jdbcTemplate.update(query, params);
            if (affectedRows == 0) {
                log.warn(EMPLOYEE_NOT_FOUND_MESSAGE.formattedMessage(employee.id()));
                return Optional.empty();
            }
            return Optional.of(employee);
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM employees WHERE id = :id";

        Map<String, Object> params = createParamsForDelete(id);

        try {
            int affectedRows = jdbcTemplate.update(query, params);
            return affectedRows > 0;
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }

    }

    private Map<String, Object> createParamsForDelete(String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return result;
    }

    private Map<String, Object> createParams(Employee employee) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", String.valueOf(employee.id()));
        result.put("firstName", employee.firstName());
        result.put("lastName", employee.lastName());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long nextId() {
        String query = "SELECT NEXTVAL('EMPLOYEE_ID_SEQ')";

        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(query, new HashMap<>(), Long.class)).orElseThrow(
                    () -> new IllegalStateException(FAIL_GET_NEXT_ID_NUMBER_MESSAGE.message()));
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE.message(), e);
            throw e;
        }
    }

    private Employee mapToEmployee(EmployeeRecord employeeRecord) {
        return new Employee(employeeRecord.id(), employeeRecord.firstName(),
            employeeRecord.lastName());
    }
}
