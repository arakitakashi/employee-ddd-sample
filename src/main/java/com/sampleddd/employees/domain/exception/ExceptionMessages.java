package com.sampleddd.employees.domain.exception;

/**
 * 例外メッセージの管理クラス。
 */
public enum ExceptionMessages {
    /**
     * 予期しないエラーが発生した場合にクライアントに返すレスポンスに使用します。
     */
    UNEXPECTED_ERROR_MESSAGE_FOR_RESPONSE("system error has occurred."),

    /**
     * 予期しないエラーが発生した場合のログ出力に使用します。
     */
    UNEXPECTED_ERROR_MESSAGE_FOR_LOG("an unexpected error has occurred."),

    /**
     * ID に対応する従業員情報が存在しない場合に使用します。
     */
    EMPLOYEE_NOT_FOUND_MESSAGE("specified employee [id = %s] is not found."),

    /**
     * AddressRepository で発番された ID 番号が取得できなかった場合に使用します。
     */
    FAIL_GET_NEXT_ID_NUMBER_MESSAGE("failed to fetch next address ID from sequence."),

    /**
     * ID が正の値でない場合に使用します。
     */
    ID_NOT_POSITIVE_VALUE_MESSAGE("employee ID must be positive"),

    /**
     * ID の最大長を超えた場合に使用します。
     */
    ID_MAX_LENGTH_EXCEEDED_MESSAGE("employee ID must not exceed the maximum length"),

    /**
     * 名が空または Null だった場合に使用します。
     */
    FIRST_NAME_NOT_BLANK_MESSAGE("firstName must not be blank"),

    /**
     * 名の最大長を超えた場合に使用します。
     */
    FIRST_NAME_MAX_LENGTH_EXCEEDED_MESSAGE("firstName must not exceed the maximum length"),

    /**
     * 姓が空または Null だった場合に使用します。
     */
    LAST_NAME_NOT_BLANK_MESSAGE("lastName must not be blank"),

    /**
     * 姓の最大長を超えた場合に使用します。
     */
    LAST_NAME_MAX_LENGTH_EXCEEDED_MESSAGE("lastName must not exceed the maximum length"),

    /**
     * DataAccessException 発生時のログ出力に使用します。
     */
    DATABASE_ACCESS_ERROR_MESSAGE("database Access Error");

    private final String message;

    /**
     * コンストラクタ。
     *
     * @param message 例外メッセージ
     */
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

    /**
     * 引数をもとにフォーマットされたメッセージを返します。
     *
     * @param args フォーマットのための引数。
     * @return フォーマットされたメッセージ文字列。
     */
    public String formattedMessage(Object... args) {
        return String.format(message, args);
    }
}
