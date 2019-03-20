package com.dawid.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "production_worker")
public class ProductionWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotBlank
    @Column(name = "name")
    private String name;
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(mappedBy = "productionWorker", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<QuiltedIndex> quiltedIndex;
    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<QuiltingData> quiltingData;

    public ProductionWorker(@NotBlank String name, @NotBlank String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public ProductionWorker() {
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

    @JsonIgnore
    public List<QuiltedIndex> getQuiltedIndex() {
        return quiltedIndex;
    }

    public void setQuiltedIndex(List<QuiltedIndex> quiltedIndex) {
        this.quiltedIndex = quiltedIndex;
    }

    @JsonIgnore
    public List<QuiltingData> getQuiltingData() {
        return quiltingData;
    }

    public void setQuiltingData(List<QuiltingData> quiltingData) {
        this.quiltingData = quiltingData;
    }

    @Override
    public String toString() {
        return "ProductionWorker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
