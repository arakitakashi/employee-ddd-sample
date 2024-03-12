package com.sampleddd.employees.domain.exception;

public enum ExceptionMessages {
    /**
     * ID が正の値でない場合に使用します。
     */
    ID_NOT_POSITIVE_VALUE_MESSAGE("Employee ID must be positive"),

    /**
     * 名が空またはNullだった場合に使用します。
     */
    FIRST_NAME_NOT_BLANK_MESSAGE("First name must not be blank"),

    /**
     * 姓が空またはNullだった場合に使用します。
     */
    LAST_NAME_NOT_BLANK_MESSAGE("Last name must not be blank"),

    /**
     * DataAccessException発生時のログ出力に使用します。
     */
    DATABASE_ACCESS_ERROR_MESSAGE("database Access Error");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    /**
     * 例外のメッセージを返します。
     *
     * @return 例外メッセージの文字列。
     */
    public String message() {
        return message;
    }
}
