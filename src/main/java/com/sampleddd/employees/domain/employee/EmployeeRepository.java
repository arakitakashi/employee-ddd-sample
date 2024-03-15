package com.sampleddd.employees.domain.employee;

import java.util.List;
import java.util.Optional;

/**
 * 従業員情報のリポジトリのインターフェース。従業員情報の検索、登録、更新、削除の操作を定義します。
 */
public interface EmployeeRepository {
    /**
     * 全ての従業員情報を取得します。
     *
     * @return 従業員情報のリスト
     */
    List<Employee> findAll();

    /**
     * 指定されたIDの従業員情報を取得します。
     *
     * @param id ID。
     * @return 従業員情報。
     */
    Optional<Employee> findById(String id);

    /**
     * 指定された従業員情報を登録します。
     *
     * @param employee
     * @return
     */
    long register(Employee employee);

    /**
     * データベースから付番される次のIDの値を返します。
     *
     * @return 付番された新しいIDの値
     */
    long nextId();
}
