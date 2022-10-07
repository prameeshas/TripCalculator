package com.littlepaydemo.tripcalculator.util.enums;

public enum Stop {
    STOP1("Stop" , 1),
    STOP2("Stop", 2),
    STOP3("Stop", 3);
    private String name;
    private int number;

    Stop(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
