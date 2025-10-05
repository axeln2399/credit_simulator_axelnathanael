package com.axelnathanael.cresim.helper;

import java.math.BigDecimal;

public enum VehicleType {
    MOBIL(BigDecimal.valueOf(0.08)),
    MOTOR(BigDecimal.valueOf(0.09));

    private final BigDecimal baseInterestRate;

    VehicleType(BigDecimal baseInterestRate) {
        this.baseInterestRate = baseInterestRate;
    }

    public BigDecimal getBaseInterestRate() {
        return baseInterestRate;
    }

    public static VehicleType fromString(String input) {
        for (VehicleType type : values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Jenis kendaraan tidak valid: " + input);
    }
}
