package com.dawid.ems.entity;

import com.dawid.ems.payload.QuilterStatistics;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "quilting_data")
public class QuiltingData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "operator_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductionWorker operator;
    @OneToMany(mappedBy = "quiltingData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuiltedIndex> quiltedIndices;
    //transient fields
    @Transient
    private QuilterStatistics quilterStatistics;

    public QuilterStatistics getQuilterStatistics() {
        return quilterStatistics;
    }

    public void setQuilterStatistics(QuilterStatistics quilterStatistics) {
        this.quilterStatistics = quilterStatistics;
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

    public ProductionWorker getOperator() {
        return operator;
    }

    public void setOperator(ProductionWorker operator) {
        this.operator = operator;
    }

    public List<QuiltedIndex> getQuiltedIndices() {
        return quiltedIndices;
    }

    public void setQuiltedIndices(List<QuiltedIndex> quiltedIndices) {
        this.quiltedIndices = quiltedIndices;
    }

    @Override
    public String toString() {
        return "QuiltingData{" +
                "id=" + id +
                ", date=" + date +
                ", operator=" + operator +
                ", quiltedIndices=" + quiltedIndices +
                '}';
    }
}
