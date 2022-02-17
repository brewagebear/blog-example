package io.github.dailyworker.springproducingwsexample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import java.io.IOException;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebServiceConfig.class)
class CountryEndpointTest {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    private static final Resource schema = new ClassPathResource("countries.xsd");

    @Autowired
    ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @BeforeEach
    public void setUp() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void getCountry() throws IOException {
        Source getCountryRequest = new StringSource("<gs:getCountryRequest xmlns:gs='" + NAMESPACE_URI + "'>" + "<gs:name>Spain</gs:name>" + "</gs:getCountryRequest>");

        Source getCountryResponse = new StringSource("<ns2:getCountryResponse xmlns:ns2='" + NAMESPACE_URI + "'>" + "<ns2:country>" + "<ns2:name>Spain</ns2:name>" +
                "<ns2:population>46704314</ns2:population>" + "<ns2:capital>Madrid</ns2:capital>" + "<ns2:currency>EUR</ns2:currency>" + "</ns2:country>" + "</ns2:getCountryResponse>");

        mockClient
                .sendRequest(withPayload(getCountryRequest))
                .andExpect(noFault())
                .andExpect(payload(getCountryResponse))
                .andExpect(validPayload(schema));
    }

}