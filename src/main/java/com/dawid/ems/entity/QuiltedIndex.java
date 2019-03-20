package com.dawid.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "quilted_index")
public class QuiltedIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "rejected_quantity")
    private int rejectedQuantity;
    @Column(name = "quilter_number")
    private int quilterNumber;
    @ManyToOne
    @JoinColumn(name = "production_worker_id")
    private ProductionWorker productionWorker;
    @ManyToOne
    @JoinColumn(name = "quilting_data_id")
    private QuiltingData quiltingData;

    public QuiltedIndex() {
    }

    public QuiltedIndex(String itemName, int quantity, int rejectedQuantity, ProductionWorker productionWorker, QuiltingData quiltingData, int quilterNumber) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.rejectedQuantity = rejectedQuantity;
        this.productionWorker = productionWorker;
        this.quiltingData = quiltingData;
        this.quilterNumber = quilterNumber;
    }

    public int getQuilterNumber() {
        return quilterNumber;
    }

    public void setQuilterNumber(int quilterNumber) {
        this.quilterNumber = quilterNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(int rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public ProductionWorker getProductionWorker() {
        return productionWorker;
    }

    public void setProductionWorker(ProductionWorker productionWorker) {
        this.productionWorker = productionWorker;
    }

    @JsonIgnore
    public QuiltingData getQuiltingData() {
        return quiltingData;
    }

    public void setQuiltingData(QuiltingData quiltingData) {
        this.quiltingData = quiltingData;
    }

    @Override
    public String toString() {
        return "QuiltedIndex{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", rejectedQuantity=" + rejectedQuantity +
                ", productionWorker=" + productionWorker +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuiltedIndex)) return false;
        QuiltedIndex that = (QuiltedIndex) o;
        return id == that.id &&
                quantity == that.quantity &&
                rejectedQuantity == that.rejectedQuantity &&
                quilterNumber == that.quilterNumber &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(productionWorker, that.productionWorker) &&
                Objects.equals(quiltingData, that.quiltingData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName, quantity, rejectedQuantity, quilterNumber, productionWorker, quiltingData);
    }
}
