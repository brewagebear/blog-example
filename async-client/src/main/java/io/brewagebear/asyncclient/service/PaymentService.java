package io.brewagebear.asyncclient.service;

import io.brewagebear.asyncclient.dto.ApiRequest;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyApiService {

    private final AsyncService asyncService;

    public ThirdPartyApiService(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    public void scarp(ApiRequest apiRequest) throws InterruptedException {
        asyncService.requestToExternalApi(apiRequest)
            .thenApply()
    }

}
