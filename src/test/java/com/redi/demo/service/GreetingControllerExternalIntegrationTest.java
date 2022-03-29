package com.redi.demo.service;

import com.redi.demo.domain.Greeting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingControllerExternalIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() {
        //Arrange
        String greetName = "testing";
        String requestUrl = "http://localhost:" + port + "/greeting?name=" + greetName;

        //Act
        Greeting result = restTemplate.getForObject(requestUrl, Greeting.class);

        //Assert
        assertThat(result.getContent()).isEqualTo("Hello, testing!");
    }
}
