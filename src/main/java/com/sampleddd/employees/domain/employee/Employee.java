package com.sampleddd.employees.domain.employee;

import static com.sampleddd.employees.domain.exception.ExceptionMessages.FIRST_NAME_MAX_LENGTH_EXCEEDED_MESSAGE;
import static com.sampleddd.employees.domain.exception.ExceptionMessages.FIRST_NAME_NOT_BLANK_MESSAGE;
import static com.sampleddd.employees.domain.exception.ExceptionMessages.ID_MAX_LENGTH_EXCEEDED_MESSAGE;
import static com.sampleddd.employees.domain.exception.ExceptionMessages.ID_NOT_POSITIVE_VALUE_MESSAGE;
import static com.sampleddd.employees.domain.exception.ExceptionMessages.LAST_NAME_MAX_LENGTH_EXCEEDED_MESSAGE;
import static com.sampleddd.employees.domain.exception.ExceptionMessages.LAST_NAME_NOT_BLANK_MESSAGE;
import static io.micrometer.common.util.StringUtils.isBlank;

/**
 * 従業員エンティティを表すクラス。従業員の基本情報である名前、姓、ID を保持します。
 *
 * @param id        従業員ID。
 * @param firstName 従業員の名前。
 * @param lastName  従業員の姓。
 */
public record Employee(long id, String firstName, String lastName) {

    private static final int ID_MAX_LENGTH = 10;
    private static final int FIRST_NAME_MAX_LENGTH = 100;
    private static final int LAST_NAME_MAX_LENGTH = 100;

    /**
     * 従業員エンティティのコンストラクタ。
     */
    public Employee {
        if (id <= 0) {
            throw new IllegalArgumentException(ID_NOT_POSITIVE_VALUE_MESSAGE.message());
        }
        if (String.valueOf(id).length() > ID_MAX_LENGTH) {
            throw new IllegalArgumentException(ID_MAX_LENGTH_EXCEEDED_MESSAGE.message());
        }
        if (isBlank(firstName)) {
            throw new IllegalArgumentException(FIRST_NAME_NOT_BLANK_MESSAGE.message());
        }
        if (firstName.length() > FIRST_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(FIRST_NAME_MAX_LENGTH_EXCEEDED_MESSAGE.message());
        }
        if (isBlank(lastName)) {
            throw new IllegalArgumentException(LAST_NAME_NOT_BLANK_MESSAGE.message());
        }
        if (lastName.length() > LAST_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(LAST_NAME_MAX_LENGTH_EXCEEDED_MESSAGE.message());
        }
    }
}
