package com.sampleddd.employees.domain.employee;

import java.util.List;

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
}
