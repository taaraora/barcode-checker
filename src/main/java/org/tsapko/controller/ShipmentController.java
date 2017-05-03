package org.tsapko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tsapko.entity.Shipment;
import org.tsapko.service.ShipmentService;

import javax.validation.Valid;

@Controller
public class ShipmentController {

    private static Logger logger = LoggerFactory.getLogger(ShipmentController.class);


    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping(value = "/shipment/{barcode}",
            method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    Shipment getShipment(@PathVariable String barcode) {

        return shipmentService.getShipment(barcode);
    }

    @RequestMapping(value = "/shipment/{barcode}",
            method = RequestMethod.POST,
            produces = "application/json")
    public @ResponseBody
    Shipment postShipment(@PathVariable @Valid String barcode) {

        return shipmentService.saveShipment(barcode);
    }

}
