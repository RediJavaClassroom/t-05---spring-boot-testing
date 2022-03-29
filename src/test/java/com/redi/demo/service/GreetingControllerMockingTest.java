package com.redi.demo.service;

import com.redi.demo.controller.GreetingController;
import com.redi.demo.domain.Greeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GreetingControllerMockingTest {

    GreetingController greetingController;

    GreetingService greetingService = Mockito.mock(GreetingService.class);

    @BeforeEach
    public void setup() {
        Mockito.reset(greetingService);
        greetingController = new GreetingController(greetingService);
    }

    @Test
    public void testGreetShouldGreatWithName() {
        //Arrange
        String greetName = "testing";
        Greeting mockResult = new Greeting(1L, "something random!");
        when(greetingService.greet(greetName)).thenReturn(mockResult);

        //Act
        Greeting result = greetingController.greet(greetName);

        //Assert
        assertThat(result.getContent()).isEqualTo("something random!");
        verify(greetingService).greet(greetName);
    }
}