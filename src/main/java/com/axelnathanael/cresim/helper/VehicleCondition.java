package com.axelnathanael.cresim.helper;

import java.math.BigDecimal;

public enum VehicleCondition {
    BARU(BigDecimal.valueOf(0.35)),
    BEKAS(BigDecimal.valueOf(0.25));

    private final BigDecimal minDpPercentage;

    VehicleCondition(BigDecimal minDpPercentage) {
        this.minDpPercentage = minDpPercentage;
    }

    public BigDecimal getMinDpPercentage() {
        return minDpPercentage;
    }

    public static VehicleCondition fromString(String input) {
        for (VehicleCondition condition : values()) {
            if (condition.name().equalsIgnoreCase(input)) {
                return condition;
            }
        }
        throw new IllegalArgumentException("Kondisi kendaraan tidak valid: " + input);
    }
}
