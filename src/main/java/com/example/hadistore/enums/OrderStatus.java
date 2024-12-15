package com.example.hadistore.enums;

public enum OrderStatus {
    PENDING(0),
    DELIVED(1),
    SUCCESS(2),
    CANCELLED(3);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderStatus fromValue(int value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value for OrderStatus: " + value);
    }
}
