package com.dawid.ems.entity;

import com.dawid.ems.enumeration.Cover;
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
    //transient fields
    @Transient
    private Double lmt;
    @Transient
    private Double lossLmt;

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


    private double calculateLmt(int quantity, Cover itemName) {
        switch (itemName) {
            case MALFORS_75: {
                return quantity * 0.87;
            }
            case MALFORS_80: {
                return quantity * 0.92;
            }
            case MALFORS_90: {
                return quantity * 1.01;
            }
            case MALFORS_140: {
                return quantity * 1.51;
            }
            case MALFORS_160: {
                return quantity * 1.71;
            }
            case MOSHULT_75: {
                return quantity * 0.84;
            }
            case MOSHULT_80: {
                return quantity * 0.89;
            }
            case MOSHULT_90: {
                return quantity * 0.99;
            }
            case MOSHULT_140: {
                return quantity * 1.49;
            }
            case MOSHULT_160: {
                return quantity * 1.69;
            }
            default: {
                return 0.0;
            }
        }
    }

    public Double getLmt() {
        setLmt(this.quantity, this.itemName);
        return lmt;
    }

    public Double getLossLmt() {
        setLossLmt(this.rejectedQuantity, this.itemName);
        return lossLmt;
    }

    private void setLmt(int quantity, String itemName) {
        this.lmt = calculateLmt(quantity, Cover.valueOf(itemName));
    }

    private void setLossLmt(int quantity, String itemName) {
        this.lossLmt = calculateLmt(quantity, Cover.valueOf(itemName));
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
