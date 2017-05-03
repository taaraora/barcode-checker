package org.tsapko.entity;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Shipment implements Serializable{

    public Shipment() {
    }

    public Shipment(String barcode) {
        this.barcode = barcode;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //todo change to sequence
    private Long id;

    @NotNull
    @Size(min = 13, max = 25)
    @Column(name="barcode")
    private String barcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Shipment{barcode='" + barcode + "\'}";
    }
}
