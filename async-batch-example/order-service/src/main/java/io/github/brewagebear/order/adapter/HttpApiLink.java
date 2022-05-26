package io.github.brewagebear.order.adapter;

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

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }
}
