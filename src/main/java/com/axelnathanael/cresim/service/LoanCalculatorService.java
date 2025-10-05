package com.axelnathanael.cresim.service;


import com.axelnathanael.cresim.model.LoanModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.axelnathanael.cresim.helper.Constant.*;

public class LoanCalculatorService {

    public List<String> calculateInstallments(LoanModel loanModel) {
        List<String> result = new ArrayList<>();

        BigDecimal principal = loanModel.getTotalLoanAmount().subtract(loanModel.getDownPaymentAmount()); // pokok awal
        BigDecimal baseRate = loanModel.getVehicleType().getBaseInterestRate(); //sesuai kendaraan
        int tenure = loanModel.getLoanTenureYears();

        BigDecimal totalMonthlySum = BigDecimal.ZERO;

        for (int year = 1; year <= tenure; year++) {
            if (principal.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal currentRate = computeRate(baseRate, year);

            BigDecimal currentLoan = principal.multiply(BigDecimal.ONE.add(currentRate));

            int remainingMonths = (tenure - (year - 1)) * MONTH_A_YEAR;

            BigDecimal monthlyInstallment = currentLoan
                    .divide(BigDecimal.valueOf(remainingMonths), 2, RoundingMode.HALF_UP);

            BigDecimal yearlyPayment = monthlyInstallment.multiply(BigDecimal.valueOf(MONTH_A_YEAR));

            result.add(String.format("Tahun %d: Rp %, .2f/bln , Suku Bunga: %.2f%%",
                    year,
                    monthlyInstallment,
                    currentRate.multiply(BigDecimal.valueOf(100)).doubleValue()));

            principal = currentLoan.subtract(yearlyPayment);
            if (principal.compareTo(BigDecimal.ZERO) < 0) principal = BigDecimal.ZERO;
        }
        return result;
    }

    private BigDecimal computeRate(BigDecimal baseRate, int year) {
        if (year == 1) return baseRate;

        BigDecimal rate = baseRate.add(ANNUAL_INCREMENT); // add 0.1% at year >= 2
        int biennialBlocks = (year - 1) / 2; // year3 -> 1, year4 ->1, year5 ->2...
        if (biennialBlocks > 0) {
            rate = rate.add(BIENNIAL_INCREMENT.multiply(BigDecimal.valueOf(biennialBlocks)));
        }
        return rate;
    }
}
