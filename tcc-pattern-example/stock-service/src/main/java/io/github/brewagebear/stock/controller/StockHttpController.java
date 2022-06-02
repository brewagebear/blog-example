package io.github.brewagebear.stock.controller;

import io.github.brewagebear.stock.dto.HttpApiLink;
import io.github.brewagebear.stock.dto.StockAdjustment;
import io.github.brewagebear.stock.entity.ReservedStock;
import io.github.brewagebear.stock.service.StockService;
import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockHttpController {

    private final StockService stockService;

    public StockHttpController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<HttpApiLink> tryStockAdjustment(@RequestBody StockAdjustment stockAdjustment) {
        final ReservedStock reservedStock = stockService.reserveStock(stockAdjustment);

        final HttpApiLink participantLink = buildParticipantLink(reservedStock.getId(), reservedStock.getExpires());

        return new ResponseEntity<>(participantLink, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> confirmStockAdjustment(@PathVariable Long id) {
        try {
            stockService.confirmStock(id);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelStockAdjustment(@PathVariable Long id) {
        stockService.cancelStock(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private HttpApiLink buildParticipantLink(Long id, LocalDateTime expires) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();

        return new HttpApiLink(location, expires);
    }
}
