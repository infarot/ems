package com.dawid.ems.payload;

public class QuilterStatistics {

    private Double lmtQ1;
    private Double lmtQ2;
    private Double lmtQ3;
    private Double lossQ1;
    private Double lossQ2;
    private Double lossQ3;
    private Double totalLmt;
    private Double totalLoss;

    public QuilterStatistics(Double lmtQ1, Double lmtQ2, Double lmtQ3, Double lossQ1, Double lossQ2, Double lossQ3, Double totalLmt, Double totalLoss) {
        this.lmtQ1 = lmtQ1;
        this.lmtQ2 = lmtQ2;
        this.lmtQ3 = lmtQ3;
        this.lossQ1 = lossQ1;
        this.lossQ2 = lossQ2;
        this.lossQ3 = lossQ3;
        this.totalLmt = totalLmt;
        this.totalLoss = totalLoss;
    }

    public QuilterStatistics() {
    }

    public Double getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(Double totalLoss) {
        this.totalLoss = totalLoss;
    }

    public Double getLmtQ1() {
        return lmtQ1;
    }

    public void setLmtQ1(Double lmtQ1) {
        this.lmtQ1 = lmtQ1;
    }

    public Double getLmtQ2() {
        return lmtQ2;
    }

    public void setLmtQ2(Double lmtQ2) {
        this.lmtQ2 = lmtQ2;
    }

    public Double getLmtQ3() {
        return lmtQ3;
    }

    public void setLmtQ3(Double lmtQ3) {
        this.lmtQ3 = lmtQ3;
    }

    public Double getLossQ1() {
        return lossQ1;
    }

    public void setLossQ1(Double lossQ1) {
        this.lossQ1 = lossQ1;
    }

    public Double getLossQ2() {
        return lossQ2;
    }

    public void setLossQ2(Double lossQ2) {
        this.lossQ2 = lossQ2;
    }

    public Double getLossQ3() {
        return lossQ3;
    }

    public void setLossQ3(Double lossQ3) {
        this.lossQ3 = lossQ3;
    }

    public Double getTotalLmt() {
        return totalLmt;
    }

    public void setTotalLmt(Double totalLmt) {
        this.totalLmt = totalLmt;
    }

    @Override
    public String toString() {
        return "QuilterStatistics{" +
                "lmtQ1=" + lmtQ1 +
                ", lmtQ2=" + lmtQ2 +
                ", lmtQ3=" + lmtQ3 +
                ", lossQ1=" + lossQ1 +
                ", lossQ2=" + lossQ2 +
                ", lossQ3=" + lossQ3 +
                ", totalLmt=" + totalLmt +
                '}';
    }
}
