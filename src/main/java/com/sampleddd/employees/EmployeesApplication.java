package com.sampleddd.employees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 従業員管理システムの起動クラスです。
 */
@SpringBootApplication
public class EmployeesApplication {

    /**
     * アプリケーションのエントリーポイントです。
     *
     * @param args コマンドライン引数。
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployeesApplication.class, args);
    }
}
