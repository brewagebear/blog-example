package io.github.brewagebear.stock.dto;

import java.net.URI;
import java.time.LocalDateTime;

public class HttpApiLink {
    private URI uri;

    private LocalDateTime expires;

    public HttpApiLink() {
    }

    public HttpApiLink(URI uri, LocalDateTime expires) {
        this.uri = uri;
        this.expires = expires;
    }

    public URI getUri() {
        return uri;
    }

    public LocalDateTime getExpires() {
        return expires;
    }
}
