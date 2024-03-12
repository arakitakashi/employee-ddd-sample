package com.sampleddd.employees.infrastructure;

/**
 * 従業員情報を表すクラス。リポジトリにおいてデータベースから取得した住所情報をマッピングします。
 *
 * @param id        従業員ID。
 * @param firstName 名。
 * @param lastName  姓。
 */
public record EmployeeRecord(long id, String firstName, String lastName) {
}
