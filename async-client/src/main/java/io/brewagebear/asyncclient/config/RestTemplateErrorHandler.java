package com.szs.szshomework.global.exception.handler;

import static com.szs.szshomework.global.exception.ApplicationExceptionCode.UNKNOWN_CLIENT_EXCEPTION;
import static com.szs.szshomework.global.exception.ApplicationExceptionCode.UNKNOWN_SEVER_EXCEPTION;

import com.szs.szshomework.global.exception.RestTemplateException;
import java.io.IOException;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {


    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().series() == Series.CLIENT_ERROR
            || response.getStatusCode().series() == Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().series() == Series.SERVER_ERROR) {
            throw new RestTemplateException(UNKNOWN_SEVER_EXCEPTION);
        } else if (response.getStatusCode().series() == Series.CLIENT_ERROR) {
            throw new RestTemplateException(UNKNOWN_CLIENT_EXCEPTION);
        }
    }
}
