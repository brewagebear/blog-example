package io.github.brewagebear.order.adapter;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.net.URI;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

public class HttpApiMessage {
    private final String url;
    private final Body requestBody;

    public HttpApiMessage(String url, Body requestBody) {
        this.url = url;
        this.requestBody = requestBody;
    }

    public String getUrl() {
        return url;
    }

    public RequestEntity<Map<String, Object>> toRequestEntity() {
        return RequestEntity.post(URI.create(this.url))
            .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .body(this.requestBody.toMap());
    }
}
