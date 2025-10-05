package com.axelnathanael.cresim.model;

import com.axelnathanael.cresim.helper.VehicleCondition;
import com.axelnathanael.cresim.helper.VehicleType;

import java.math.BigDecimal;
import java.time.Year;

public class LoanModel {

    private VehicleType vehicleType;
    private VehicleCondition condition;
    private Year vehicleYear;
    private BigDecimal totalLoanAmount;
    private int loanTenureYears;
    private BigDecimal downPaymentAmount;

    public LoanModel() {
    }

    public LoanModel(VehicleType vehicleType, VehicleCondition condition, Year vehicleYear, BigDecimal totalLoanAmount, int loanTenureYears, BigDecimal downPaymentAmount) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.vehicleYear = vehicleYear;
        this.totalLoanAmount = totalLoanAmount;
        this.loanTenureYears = loanTenureYears;
        this.downPaymentAmount = downPaymentAmount;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleCondition getCondition() {
        return condition;
    }

    public void setCondition(VehicleCondition condition) {
        this.condition = condition;
    }

    public Year getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(Year vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public BigDecimal getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(BigDecimal totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public int getLoanTenureYears() {
        return loanTenureYears;
    }

    public void setLoanTenureYears(int loanTenureYears) {
        this.loanTenureYears = loanTenureYears;
    }

    public BigDecimal getDownPaymentAmount() {
        return downPaymentAmount;
    }

    public void setDownPaymentAmount(BigDecimal downPaymentAmount) {
        this.downPaymentAmount = downPaymentAmount;
    }
}
