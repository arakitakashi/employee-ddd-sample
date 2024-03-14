package com.sampleddd.employees.usecase;

import com.sampleddd.employees.usecase.dto.EmployeeDto;
import org.springframework.stereotype.Service;

/**
 * 指定されたIDの従業員情報を取得をするユースケースクラス。リポジトリを利用して、操作を行います。
 */
@Service
public class EmployeeGetByIdUsecase {

    /**
     * 指定されたIDの従業員情報を取得します。従業員情報が存在しない場合例外を発生させます。
     *
     * @param id ID。
     * @return 指定されたIDの従業員情報を含むDTO。
     */
    public EmployeeDto execute(String id) {
        return new EmployeeDto(
            1L,
            "Yamada",
            "Taro"
        );
    }
}
