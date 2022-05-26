package io.github.brewagebear.stock;

import io.github.brewagebear.stock.entity.Stock;
import io.github.brewagebear.stock.repository.StockRepository;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(StockRepository stockRepository) {
        return (args) -> {
            IntStream.rangeClosed(1, 10).forEach(index ->{
                stockRepository.save(Stock.builder()
                    .productId(String.valueOf(index))
                    .availableStockQty((long) (Math.random() * 20))
                    .build());
            });
        };
    }
}
