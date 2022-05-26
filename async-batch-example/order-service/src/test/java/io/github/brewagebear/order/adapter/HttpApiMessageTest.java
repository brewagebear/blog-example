package io.github.brewagebear.order.adapter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.RequestEntity;

class HttpApiMessageTest {

    @Test
    public void test() {

        Body body = new Body();

        body.put("test", "test");

        HttpApiMessage httpApiMessage = new HttpApiMessage("http://test.com", body);
        RequestEntity<Body> request = httpApiMessage.toRequestEntity();

        System.out.println(request.toString());
    }

}