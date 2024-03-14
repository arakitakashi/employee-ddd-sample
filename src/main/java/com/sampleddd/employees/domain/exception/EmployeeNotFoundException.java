package com.sampleddd.employees.domain.exception;

import static com.sampleddd.employees.domain.exception.ExceptionMessages.EMPLOYEE_NOT_FOUND_MESSAGE;

/**
 * 従業員情報が見つからない場合にスローされる例外。この例外は、従業員情報の検索で対象の従業員情報がデータベースに存在しない場合に使用されます。
 */
public class EmployeeNotFoundException extends RuntimeException {
    /**
     * 指定されたメッセージを持つ{@link EmployeeNotFoundException}を構築します。
     *
     * @param id
     */
    public EmployeeNotFoundException(String id) {
        super(EMPLOYEE_NOT_FOUND_MESSAGE.formattedMessage(id));
    }
}
