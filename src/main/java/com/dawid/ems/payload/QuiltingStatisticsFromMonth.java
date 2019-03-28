package com.dawid.ems.payload;

public class QuiltingStatisticsFromMonth {
    private double averageLmt;
    private double averageTotalLoss;
    private int month;

    public QuiltingStatisticsFromMonth(double averageLmt, double averageTotalLoss, int month) {
        this.averageLmt = averageLmt;
        this.averageTotalLoss = averageTotalLoss + 0.01;
        this.month = month;
    }

    public QuiltingStatisticsFromMonth() {
    }

    public double getAverageLmt() {
        return averageLmt;
    }

    public void setAverageLmt(double averageLmt) {
        this.averageLmt = averageLmt;
    }

    public double getAverageTotalLoss() {
        return averageTotalLoss;
    }

    public void setAverageTotalLoss(double averageTotalLoss) {
        this.averageTotalLoss = averageTotalLoss;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
