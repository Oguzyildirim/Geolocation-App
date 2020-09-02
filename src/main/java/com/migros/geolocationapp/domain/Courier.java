package com.migros.geolocationapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A Store.
 */
@Entity
@Table(name = "courier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Courier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "courier_id", nullable = false, unique = true)
    private String courierID;

    @NotNull
    @Column(name = "lat", nullable = false)
    private Double lat;

    @NotNull
    @Column(name = "lng", nullable = false)
    private Double lng;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourierID() {
        return courierID;
    }

    public void setCourierID(String courierID) {
        this.courierID = courierID;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Courier)) return false;
        Courier courier = (Courier) o;
        return getId().equals(courier.getId()) &&
                getCourierID().equals(courier.getCourierID()) &&
                getLat().equals(courier.getLat()) &&
                getLng().equals(courier.getLng()) &&
                getTimestamp().equals(courier.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCourierID(), getLat(), getLng(), getTimestamp());
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", courierID='" + courierID + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", timestamp=" + timestamp +
                '}';
    }
}
