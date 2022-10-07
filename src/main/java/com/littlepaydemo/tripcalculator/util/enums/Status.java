package com.littlepaydemo.tripcalculator.util.enums;

public enum Status {
    COMPLETE("COMPLETE"),
    INCOMPLETE("INCOMPLETE"),
    CANCELLED("CANCELLED");
    private String name;

    Status(String name) {
        this.name = name;
    }
}
