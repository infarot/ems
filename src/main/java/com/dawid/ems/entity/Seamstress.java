package com.dawid.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Length(min = 2)
    @Length(max = 15)
    private String name;
    @Column(name = "last_name")
    @Length(min = 2)
    @Length(max = 15)
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

    public Seamstress(int id, String name, String lastName, Double average, Double score) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.average = average;
        this.score = score;
    }

    public Seamstress(int id, String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public Seamstress() {
    }

    @JsonIgnore
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
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
                getLastName().equals(that.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLastName());
    }

    @Override
    public int compareTo(Seamstress o) {
        return getLastName().compareTo(o.getLastName());
    }

    @Override
    public String toString() {
        return "Seamstress{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", average=" + average +
                ", score=" + score +
                '}';
    }
}
