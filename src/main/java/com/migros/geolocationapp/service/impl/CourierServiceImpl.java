package com.migros.geolocationapp.service.impl;


import com.migros.geolocationapp.domain.Courier;
import com.migros.geolocationapp.domain.Store;
import com.migros.geolocationapp.repository.CourierRepository;
import com.migros.geolocationapp.repository.StoreRepository;
import com.migros.geolocationapp.service.CourierService;
import com.migros.geolocationapp.service.dto.CourierDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Courier}.
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    private final Logger log = LoggerFactory.getLogger(CourierServiceImpl.class);

    private final StoreRepository storeRepository;
    private  final CourierRepository courierRepository;

    public CourierServiceImpl(StoreRepository storeRepository, CourierRepository courierRepository) {
        this.storeRepository = storeRepository;
        this.courierRepository = courierRepository;
    }

    /**
     * Save a courier.
     *
     * @param courierDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Courier save(CourierDTO courierDTO) {
        log.debug("Request to save Courier : {}", courierDTO);
        Courier courier = new Courier();
        if(courierRepository.findOneByCourierID(courierDTO.getCourierID()).isPresent())  {
            courier = courierRepository.findOneByCourierID(courierDTO.getCourierID()).get();
            courier.setLat(courierDTO.getLat());
            courier.setLng(courierDTO.getLng());
            courier.setTimestamp(courierDTO.getTimestamp());
            courierRepository.save(courier);
        } else {
            courier.setCourierID(courierDTO.getCourierID());
            courier.setLat(courierDTO.getLat());
            courier.setLng(courierDTO.getLng());
            courier.setTimestamp(courierDTO.getTimestamp());
        }
        return courierRepository.save(courier);
    }

    /**
     * Alert a courier.
     *
     * @param courierDTO the entity to check distance to store.
     */
    @Override
    public void alertCourier(CourierDTO courierDTO) {
        List<Store> stores = storeRepository.findAll();
        for(Store store : stores) {
            if(distFrom(store.getLat(), store.getLng(), courierDTO.getLat(), courierDTO.getLng())) {
                if (!courierRepository.findOneByCourierID(courierDTO.getCourierID()).isPresent()) {
                    log.debug("A courier has less than 100 meter distance to a MIGROS store");
                    log.debug("Courier courierID: {}  ", courierDTO.getCourierID());
                    log.debug("Store store name: {}", store.getName());
                    break;
                }
                if(checkLastCourierAlert(courierDTO)) {
                    log.debug("A courier has less than 100 meter distance to a MIGROS store");
                    log.debug("Courier courierID: {}  ", courierDTO.getCourierID());
                    log.debug("Store store name: {}", store.getName());
                }
            }
        }
    }

    /**
     * Check last alert time whether the different less than or greater than one minute
     *
     * @param courierDTO the entity to check time difference.
     */
    private boolean checkLastCourierAlert(CourierDTO courierDTO) {
        if (courierRepository.findOneByCourierID(courierDTO.getCourierID()).isPresent()) {
            Courier courier = courierRepository.findOneByCourierID(courierDTO.getCourierID()).get();
            LocalDateTime fromTemp = LocalDateTime.from(courier.getTimestamp());
            long minutes = fromTemp.until(courierDTO.getTimestamp(), ChronoUnit.MINUTES);
            return minutes > 1;
        }
        return false;
    }

    /**
     * Calculate distance function and return true if it is less than 100
     *
     * @param lat1 lat of store
     * @param lng1 lng of store
     * @param lat2 lat of courier
     * @param lng2 lng of courier
     */
    private boolean distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (earthRadius * c);
        return dist < 100;
    }

    /**
     * Get one courier by courierID.
     *
     * @param courierID the courierID of entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Courier> findOneByCourierID(String courierID) {
        log.debug("Request to get Courier by courierID: {}", courierID);
        return courierRepository.findOneByCourierID(courierID);
    }
}
