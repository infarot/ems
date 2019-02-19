package com.dawid.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "seamstress")
public class Seamstress implements Comparable<Seamstress> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(mappedBy = "seamstress", fetch = FetchType.LAZY)
    private List<Result> results;
    @Transient
    private Double average;
    @Transient
    private Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Seamstress(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    @JsonIgnore
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Seamstress() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seamstress)) return false;
        Seamstress that = (Seamstress) o;
        return getId() == that.getId() &&
                getName().equals(that.getName()) &&
                getLastName().equals(that.getLastName()) &&
                Objects.equals(getResults(), that.getResults());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLastName());
    }

    @Override
    public int compareTo(Seamstress o) {
        return getLastName().compareTo(o.getLastName());
    }
}
