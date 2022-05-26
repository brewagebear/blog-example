package io.github.brewagebear.stock.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.brewagebear.stock.dto.StockAdjustment;
import io.github.brewagebear.stock.entity.enumerate.Status;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReservedStock {
    // 3초 타임 아웃
    private static final long TIMEOUT = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String resources;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime created;

    private LocalDateTime expires;

    protected ReservedStock() {
    }

    public ReservedStock(StockAdjustment stockAdjustment) {
        try {
            this.resources = stockAdjustment.serializeJSON();
            this.status = Status.RESERVED;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.created = LocalDateTime.now();
        this.expires = created.plus(TIMEOUT, ChronoUnit.SECONDS);
    }

    public Long getId() {
        return id;
    }

    public String getResources() {
        return this.resources;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void validate() {
        validateStatus();
        validateExpired();
    }

    public void validateStatus() {
        if(this.getStatus().equals(Status.CANCELLED)  || this.getStatus().equals(Status.COMPLETED)) {
            throw new IllegalArgumentException("Invalidate Status");
        }
    }

    private void validateExpired() {
        if(LocalDateTime.now().isAfter(this.expires)) {
            throw new IllegalArgumentException("Expired");
        }
    }

}
