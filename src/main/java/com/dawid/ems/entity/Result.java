package com.dawid.ems.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Result {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "percentage_result")
    private double percentageResult;
    @Column(name = "shift")
    private char shift;
    @ManyToOne
    private Seamstress seamstress;

    public Result() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPercentageResult() {
        return percentageResult;
    }

    public void setPercentageResult(double percentageResult) {
        this.percentageResult = percentageResult;
    }

    public char getShift() {
        return shift;
    }

    public void setShift(char shift) {
        this.shift = shift;
    }

    public Seamstress getSeamstress() {
        return seamstress;
    }

    public void setSeamstress(Seamstress seamstress) {
        this.seamstress = seamstress;
    }
}
