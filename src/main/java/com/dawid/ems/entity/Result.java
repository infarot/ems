package com.dawid.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seamstress_result")
public class Result {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "percentage_result")
    private double percentageResult;
    @Column(name = "shift")
    private char shift;
    @ManyToOne
    @JoinColumn(name="seamstress_id")
    private Seamstress seamstress;

    public Result() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @JsonIgnore
    public Seamstress getSeamstress() {
        return seamstress;
    }

    public void setSeamstress(Seamstress seamstress) {
        this.seamstress = seamstress;
    }
}
