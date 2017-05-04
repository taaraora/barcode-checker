package org.tsapko.exception;

public class ShipmentNotFoundException extends RuntimeException{

    private String barcode;

    public ShipmentNotFoundException(String barcode){
        this.barcode = barcode;
    }

    public ShipmentNotFoundException(String message, String barcode){
        super(message);
        this.barcode = barcode;
    }

    public ShipmentNotFoundException(String message, Throwable cause, String barcode){
        super(message, cause);
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "ShipmentNotFoundException{" +
                "barcode='" + barcode + '\'' +
                '}';
    }
}
