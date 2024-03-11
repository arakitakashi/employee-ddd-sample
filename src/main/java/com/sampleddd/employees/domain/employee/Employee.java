package com.sampleddd.employees.domain.employee;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

/**
 * 従業員エンティティを表すクラス。従業員の基本情報である名前、姓、ID を保持します。
 *
 * @param id        従業員ID
 * @param firstName 従業員の名前
 * @param lastName  従業員の姓
 */
public record Employee(int id, String firstName, String lastName) {
    public Employee {
        if (isBlank(firstName)) throw new IllegalArgumentException("First name must not be blank");
        if (isBlank(lastName)) throw new IllegalArgumentException("Last name must not be blank");
    }
}
