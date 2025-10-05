package com.axelnathanael.cresim.model;

import java.util.List;

public class Sheet {
    private String name;
    private LoanModel vehicle;
    private List<String> listLoan;

    public Sheet() {
    }

    public Sheet(String name, LoanModel vehicle, List<String> listLoan) {
        this.name = name;
        this.vehicle = vehicle;
        this.listLoan = listLoan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoanModel getVehicle() {
        return vehicle;
    }

    public void setVehicle(LoanModel vehicle) {
        this.vehicle = vehicle;
    }

    public List<String> getListLoan() {
        return listLoan;
    }

    public void setListLoan(List<String> listLoan) {
        this.listLoan = listLoan;
    }
}
