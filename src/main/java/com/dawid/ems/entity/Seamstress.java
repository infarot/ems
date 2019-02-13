package com.dawid.ems.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seamstress")
public class Seamstress {

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

    public Seamstress(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

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
}
