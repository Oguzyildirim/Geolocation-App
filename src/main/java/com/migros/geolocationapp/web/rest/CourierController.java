package com.migros.geolocationapp.web.rest;

import com.migros.geolocationapp.domain.Courier;
import com.migros.geolocationapp.service.CourierService;
import com.migros.geolocationapp.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for managing {@link com.migros.geolocationapp.domain.Courier}.
 */
@RestController
@RequestMapping("/api")
public class CourierController {

    private final Logger log = LoggerFactory.getLogger(CourierController.class);

    private static final String ENTITY_NAME = "courierserviceCourier";

    @Value("${migros.clientApp.name}")
    private String applicationName;

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    /**
     * {@code GET  /couriers} : get a courier by courierID.
     * @param courierID the courier courierID code.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/couriers/")
    public ResponseEntity<Courier> getCourierByCourierID(@RequestParam(value = "courierID") String courierID) {
        log.debug("REST request to get a page of Courier by courierID");
        Optional<Courier> courier = courierService.findOneByCourierID(courierID);
        return ResponseUtil.wrapOrNotFound(courier);
    }
}