package org.tsapko.service;

import orestes.bloomfilter.BloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tsapko.controller.ShipmentController;
import org.tsapko.dao.ShipmentRepository;
import org.tsapko.entity.Shipment;
import org.tsapko.exception.handler.ShipmentNotFoundException;

@Service
public class ShipmentService {

    private static Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private BloomFilter<String> filter;

    @Transactional(readOnly = true)
    public Shipment getShipment(String barcode) throws ShipmentNotFoundException {

        if(!filter.contains(barcode)) throw new ShipmentNotFoundException(barcode);

        Shipment s = shipmentRepository.findByBarcode(barcode);
        logger.info("barcode {}, entity: {}", barcode, s);
        if (s == null) throw new ShipmentNotFoundException(barcode);

        return s;
    }

    @Transactional
    public @ResponseBody
    Shipment saveShipment(String barcode) {

        Shipment s = shipmentRepository.save(new Shipment(barcode));
        logger.info("barcode {}, entity: {}", barcode, s);
        filter.add(barcode);

        return s;
    }

}
