package com.dawid.ems.payload;

public class AveragePerAllFromMonth {
    private Double average;
    private int month;

    public AveragePerAllFromMonth(Double average, int month) {
        this.average = average;
        this.month = month;
    }

    public AveragePerAllFromMonth() {
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
