package com.redi.demo.service;

import com.redi.demo.controller.GreetingController;
import com.redi.demo.domain.Greeting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreetingServiceTest {

    GreetingService greetingService;

    @BeforeEach
    public void setup() {
        greetingService = new GreetingService();
    }

    @Test
    public void testGreetShouldGreatWithName() {
        //Arrange
        String greetName = "testing";

        //Act
        Greeting result = greetingService.greet(greetName);

        //Assert
        assertThat(result.getContent()).isEqualTo("Hello, testing!");
    }

    @Test
    public void testAssertions() {
        int value = 10;
        Integer nullValue = null;

        //equals
        assertEquals(10, value);
        assertThat(value).isEqualTo(10);

        //boolean
        assertTrue(value == 10);
        assertThat(value == 10).isTrue();
        assertFalse(value == 9);
        assertThat(value == 9).isFalse();

        //nullability
        assertNull(nullValue);
        assertThat(nullValue).isNull();
        assertNotNull(value);
        assertThat(value).isNotNull();
    }

    @Test
    public void shouldGiveAnErrorAfter10DifferentGreets() {
        //Arrange
        String greetName = "testing1234";

        //Act & Arrange
        assertThrows(RuntimeException.class, () -> greetingService.greet(greetName));
    }

    @ValueSource(strings = { "Berlin", "REDI Rocks", "My Name" })
    @ParameterizedTest
    public void shouldGreetWithValueSource(String greetName) {
        //Arrange

        //Act &
        Greeting result = greetingService.greet(greetName);

        //Arrange
        assertThat(result.getContent()).isEqualTo("Hello, " + greetName + "!");
    }

    @MethodSource("data")
    @ParameterizedTest
    public void shouldGreetWithMethodSource(String greetName) {
        //Arrange

        //Act &
        Greeting result = greetingService.greet(greetName);

        //Arrange
        assertThat(result.getContent()).isEqualTo("Hello, " + greetName + "!");
    }

    public static String[] data() {
        return new String[] { "Berlin", "REDI Rocks", "My Name" };
    }

    @AfterEach
    public void tearDown() {
        greetingService = null;
    }
}