package com.redi.demo.service;

import com.redi.demo.controller.GreetingController;
import com.redi.demo.domain.Greeting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingControllerIntegrationTest {

    @Autowired
    GreetingController greetingController;

    @Test
    public void testGreetShouldGreatWithName() {
        //Arrange
        String greetName = "testing";

        //Act
        Greeting result = greetingController.greet(greetName);


        assertThat(result.getContent()).contains("Hello, testing!");
    }
}
