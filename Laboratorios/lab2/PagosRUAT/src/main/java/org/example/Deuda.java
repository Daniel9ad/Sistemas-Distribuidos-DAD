package org.example;

import java.io.Serializable;

public class Deuda implements Serializable {
    private String ci;
    private int year;
    private String taxType;  // Puede ser "Vehículo" o "Casa"
    private double amount;

    public Deuda(String ci, int year, String taxType, double amount) {
        this.ci = ci;
        this.year = year;
        this.taxType = taxType;
        this.amount = amount;
    }

    public String getCi() {
        return ci;
    }

    public int getYear() {
        return year;
    }

    public String getTaxType() {
        return taxType;
    }

    public double getAmount() {
        return amount;
    }
}
