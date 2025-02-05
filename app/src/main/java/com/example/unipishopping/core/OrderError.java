package com.example.unipishopping.core;

public class OrderError {
    public static final int TASK_FAILED = 0;
    public static final int PRODUCT_NOTFOUND = 1;
    private final int code;
    private final String description;

    private OrderError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    static OrderError productNotFound() {
        return new OrderError(PRODUCT_NOTFOUND, "Couldn't find the product in our system!");
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
