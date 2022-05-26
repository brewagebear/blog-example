package io.github.brewagebear.order.adapter.impl;

import io.github.brewagebear.order.adapter.Body;
import io.github.brewagebear.order.adapter.HttpAdapter;
import io.github.brewagebear.order.adapter.HttpApiMessage;
import io.github.brewagebear.order.adapter.HttpApiLink;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HttpAdapterImpl implements HttpAdapter {

    private final RestTemplate restTemplate = new RestTemplate();

    private final RetryTemplate retryTemplate;

    public HttpAdapterImpl(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    @Override
    public List<HttpApiLink> doTry(List<HttpApiMessage> httpApiAdapters) {

        List<HttpApiLink> httpApiLinks = new ArrayList<>();

        httpApiAdapters.forEach(apiRequest -> {
            try {
                exchange(apiRequest);
            } catch (RestClientException e) {
                cancelAll(httpApiLinks);
                throw new RuntimeException(String.format("[URI : %s]", apiRequest.getUrl()), e);
            }
        });

        return httpApiLinks;
    }

    @Override
    public void confirmAll(List<HttpApiLink> httpApiLinks) {
        httpApiLinks.forEach(httpApiLink -> {
            try {
                retryTemplate.execute((RetryCallback<HttpApiLink, RestClientException>) context -> {
                    restTemplate.put(httpApiLink.getUri(), null);
                    return null;
                });
            } catch (RestClientException e) {
                log.error(String.format("[URI : %s]", httpApiLink.getUri().toString()), e);
            }
        });
    }

    @Override
    public void cancelAll(List<HttpApiLink> httpApiLinks) {
        httpApiLinks.forEach(httpApiLink -> {
            try {
                retryTemplate.execute((RetryCallback<HttpApiLink, RestClientException>) context -> {
                    restTemplate.delete(httpApiLink.getUri());
                    return null;
                });
            } catch (RestClientException e) {
                log.error(String.format("[URI : %s]", httpApiLink.getUri().toString()), e);
            }
        });
    }

    private void exchange(HttpApiMessage apiRequest) {
        retryTemplate.execute((RetryCallback<HttpApiLink, RestClientException>) context -> {
            URI uri = URI.create(apiRequest.getUrl());
            RequestEntity<Map<String, Object>> request = apiRequest.toRequestEntity();

            ResponseEntity<HttpApiLink> response = restTemplate.exchange(request,
                HttpApiLink.class);
            log.info("Http API URI : %s", response.getBody().getUri());

            return response.getBody();
        });
    }
}
