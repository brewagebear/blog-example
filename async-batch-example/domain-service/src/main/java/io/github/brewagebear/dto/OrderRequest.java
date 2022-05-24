package io.github.brewagebear.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private final List<BuyRequest> buyRequests = new ArrayList<>();

    public OrderRequest(List<BuyRequest> requests) {
        buyRequests.addAll(requests);
    }
}
