package io.github.brewagebear.order.adapter;

import java.util.List;

public interface HttpAdapter {
    List<HttpApiLink> doTry(List<HttpApiMessage> httpApiAdapters);
    void confirmAll(List<HttpApiLink> httpApiLinks);
    void cancelAll(List<HttpApiLink> httpApiLinks);
}
