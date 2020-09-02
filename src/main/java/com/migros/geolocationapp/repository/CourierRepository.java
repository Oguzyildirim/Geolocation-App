package com.migros.geolocationapp.repository;


import com.migros.geolocationapp.domain.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Courier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    Optional<Courier> findOneByCourierID(String courierID);
}
