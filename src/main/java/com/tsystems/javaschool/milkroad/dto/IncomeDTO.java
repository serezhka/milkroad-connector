package com.tsystems.javaschool.milkroad.dto;

import java.math.BigDecimal;

/**
 * Created by Sergey on 31.03.2016.
 */
public class IncomeDTO {
    private BigDecimal totalCash;
    private BigDecimal totalCashThisMonth;
    private BigDecimal totalCashLast7Days;

    public BigDecimal getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(final BigDecimal totalCash) {
        this.totalCash = totalCash;
    }

    public BigDecimal getTotalCashThisMonth() {
        return totalCashThisMonth;
    }

    public void setTotalCashThisMonth(final BigDecimal totalCashThisMonth) {
        this.totalCashThisMonth = totalCashThisMonth;
    }

    public BigDecimal getTotalCashLast7Days() {
        return totalCashLast7Days;
    }

    public void setTotalCashLast7Days(final BigDecimal totalCashLast7Days) {
        this.totalCashLast7Days = totalCashLast7Days;
    }
}
