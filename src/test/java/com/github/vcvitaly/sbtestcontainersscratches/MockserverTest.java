package com.github.vcvitaly.sbtestcontainersscratches;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * MockserverTest.
 *
 * @author Vitalii Chura
 */
@Testcontainers
public class MockserverTest {


    public static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());

    @Container
    public static MockServerContainer mockServer = new MockServerContainer(MOCKSERVER_IMAGE);

    @Test
    void test() throws IOException, InterruptedException {
        try (
                MockServerClient mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
        ) {
            mockServerClient
                    .when(request().withPath("/person").withQueryStringParameter("name", "peter"))
                    .respond(response().withBody("Peter the person!"));

            // ...a GET request to '/person?name=peter' returns "Peter the person!"


            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(20))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("http://%s:%s/person?name=peter", mockServer.getHost(), mockServer.getServerPort())))
                    .timeout(Duration.ofMinutes(2))
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.body())
                    .as("Expectation returns expected response body")
                    .contains("Peter the person");

        }
    }
}
