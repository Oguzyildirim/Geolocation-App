package com.migros.geolocationapp.service;


import com.migros.geolocationapp.domain.Courier;
import com.migros.geolocationapp.domain.Store;
import com.migros.geolocationapp.service.dto.CourierDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link Courier}.
 */
public interface CourierService {

    /**
     * Save a courier.
     *
     * @param courierDTO the entity to save.
     * @return the persisted entity.
     */
    Courier save(CourierDTO courierDTO);

    /**
     * Alert a courier.
     *
     * @param courierDTO the entity to check distance to store.
     */
    void alertCourier(CourierDTO courierDTO);


    /**
     * Get the "courierID" courier.
     *
     * @param courierID the id of the entity.
     * @return the entity.
     */
    Optional<Courier> findOneByCourierID(String courierID);

}
