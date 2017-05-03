package org.tsapko.persistance.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Shipment implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //todo change to sequence
    private long id;

    @Column(name="barcode")
    private String barcode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Shipment shipment = (Shipment) o;
        return Objects.equals(getBarcode(), shipment.getBarcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBarcode());
    }
}
