package io.github.brewagebear.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyRequest {

    @JsonProperty(value = "productId")
    private Long productId;

    @JsonProperty(value = "amount")
    private int amount;

    public BuyRequest(Long productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
