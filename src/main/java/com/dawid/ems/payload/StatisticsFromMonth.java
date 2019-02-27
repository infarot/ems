package com.dawid.ems.payload;

public class StatisticsFromMonth {
    private Double averagePerAll;
    private Double averageResult;
    private Double averageWorkOrganization;
    private int month;

    public StatisticsFromMonth() {
    }

    public Double getAverageWorkOrganization() {
        return averageWorkOrganization;
    }

    public void setAverageWorkOrganization(Double averageWorkOrganization) {
        this.averageWorkOrganization = averageWorkOrganization;
    }

    public Double getAveragePerAll() {
        return averagePerAll;
    }

    public void setAveragePerAll(Double averagePerAll) {
        this.averagePerAll = averagePerAll;
    }

    public Double getAverageResult() {
        return averageResult;
    }

    public void setAverageResult(Double averageResult) {
        this.averageResult = averageResult;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
