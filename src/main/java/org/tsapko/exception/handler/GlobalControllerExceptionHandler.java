package org.tsapko.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Collections;

import static java.util.Collections.singletonMap;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final String SHIPMENT_NFE_MESSAGE = "Shipment not found for barcode - ";
    private final String MESSAGE = "MESSAGE";
    private final String CONFLICT_MESSAGE = "Shipment already exists";

    private static Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler({ SQLException.class, DataAccessException.class })
    public ResponseEntity<Object> anyException(RuntimeException ex, WebRequest request) {
        logger.error("Exception raised {}", ex);
        return handleExceptionInternal(ex, "Data access exception",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> anyException(DataIntegrityViolationException ex, WebRequest request) {
        logger.error("Exception raised {}", ex);
        return handleExceptionInternal(ex, singletonMap(MESSAGE, CONFLICT_MESSAGE),
                new HttpHeaders(), HttpStatus.CONFLICT, request);

    }




    @ExceptionHandler({ ShipmentNotFoundException.class })
    public ResponseEntity<Object> shipmentNotFoundException(ShipmentNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, singletonMap(MESSAGE, SHIPMENT_NFE_MESSAGE + ex.getBarcode()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }

}
