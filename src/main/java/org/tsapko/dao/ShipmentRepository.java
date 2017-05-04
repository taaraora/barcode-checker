package org.tsapko.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.tsapko.entity.Shipment;

import java.util.List;

public interface ShipmentRepository extends CrudRepository<Shipment, Long> {

    @Query("SELECT s from Shipment s where s.barcode = :barcode")
    Shipment findByBarcode(@Param("barcode") String barcode);

    @Query(value = "SELECT s.barcode from shipment s", nativeQuery = true)
    List<String> loadAllBarcodes();

}
