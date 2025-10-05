package com.axelnathanael.cresim.helper;

import java.math.BigDecimal;
import java.util.List;


public final class Constant {

    public Constant() {
    }

    public static final BigDecimal ONE_BILLION = new BigDecimal("1000000000");
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    public static final int MONTH_A_YEAR = 12;
    public static final BigDecimal ANNUAL_INCREMENT = new BigDecimal("0.001"); // 0.1%
    public static final BigDecimal BIENNIAL_INCREMENT = new BigDecimal("0.005"); //0.5%
    public static final List<String> YES_NO = List.of("ya", "tidak");


    public static final String MOCKY_URL = "https://run.mocky.io/v3/621e2742-309f-493e-b9d4-ff168282b69e";
}
