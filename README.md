# Spring boot Testing

## What You Will Build
Unit and integration testing for the greeting application you have built before.

## What You Need
- JDK 11 or later
- Maven 3.2+

## Unit testing of the GreetingService
1. create a new java class inside the folder `src/test/java/com/redi/demo/service` with the name `GreetingServiceTest`
2. Define a variable of type `GreetingServiceTest` which we will test.
3. add a setup method to instantiate the `GreetingService` also annotate it with `@BeforeEach`
4. create a new test method to greet `testing`
5. validate that the result of should be equal to `Hello, testing!`
6. add a tearDown method and annotate it with `@AfterEach` to delete the instance of `GreetingService`

```java
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
    
    @AfterEach
    public void tearDown() {
        greetingService = null;
    }
}
```
7. right click over the method name and select Run.
8. you can also run the whole test suit by typing in the console
```shell
mvn test
```
or
```shell
mvn clean build
```
## Assertions
assertion allows us to perform different types of validations over the result of our test method.

assertions are of different types for example:
- Equality
- Boolean
- Nullability
- Exceptions
- Lists and others.
```java
class GreetingServiceTest {
    //...
    @Test
    public void testAssertions() {
        int value = 10;
    
        //equals
        assertEquals(10, value);
        assertThat(value).isEqualTo(10);
    
        //boolean
        assertTrue(value == 10);
        assertThat(value == 10).isTrue();
        assertFalse(value == 9);
        assertThat(value == 9).isFalse();
    
        //nullability
        assertNull(value);
        assertThat(value).isNull();
        assertNotNull(value);
        assertThat(value).isNotNull();
    }
}
```
you can find all the documentation about assertions in this [link](https://junit.org/junit5/docs/current/user-guide/)

## First Activity
Create the following test cases for the statistics method:
1. when no greet has been done the statistics array should be empty.
2. when greeting a name the statistics response array size is equal to 1, the value for that name should be equal to 1.
3. when greeting a name twice the statistics response array size should be equal to 1, the value for that name should be equal to 2
4. if we greet 10 times the same name the statistics the response array size should be equal to 1, the value for that name should be equal to 1
5. test that greeting 5 different names 4 times each, returns a statistics array with 5 items and each with a value of 4.

## testing exceptions
we can also assert if our code gives an exception by using the `assertThrows` method. this method receive two parameters a Exception class and a provider which can be a lambda.

in this example we are asserting that our greet method will throw an exception if there has been more than 10 different greets to our greet Services.
```java
class GreetingServiceTest {
    //...
    @Test
    public void shouldGiveAnErrorAfter10DifferentGreets() {
        //Arrange
        String greetName = "testing1234";

        //Act & Arrange
        assertThrows(RuntimeException.class, () -> greetingService.greet(greetName));
    }
}
```
1. Modify the greeting endpoint to cover this new feature.
2. make sure all the other test cases are still working

## Parametrized tests
when you want to test really similar behaviours, but we don't want to have code duplication, we can use parametrized tests

value source is for primitives and simple classes like String, but if you need a custom class like Greetings then you can use MethodSource.

other types are:
- EnumSource
- CSVSource
  -ArgumentsSource

1. add the following value source example to your test class
```java
class GreetingServiceTest {
    //...
    @ValueSource(strings = {"Berlin", "REDI Rocks", "My Name"})
    @ParameterizedTest
    public void shouldGreetWithValueSource(String greetName) {
        //Arrange

        //Act &
        Greeting result = greetingService.greet(greetName);

        //Arrange
        assertThat(result.getContent()).isEqualTo("Hello, " + greetName + "!");
    }
}
```
2. add the following method source example to your test class
```java
class GreetingServiceTest {
    //...
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
}
```
## Mockito
sometimes we will have a complex graph of dependencies in our classes for these cases we don't
want to have to instantiate every single dependency of our dependencies, instead we would like to have control over our tests by using mocks.

Mockito is one of the most famous libraries for mocking in java.
```java
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
```

it allows us to create fake versions of our test class dependencies and control how they behave to generate different use case scenarios.

## Spring Tests
Spring tests let us test the application running, Spring will make sure to start the entire spring context for us, it will start the entire web server if we want to test entirely our application.

this is good for integration testing. have in mind this will be closes to you hitting directly the endpoints of your application.

also have in mind that this are costly tests and therefore run slower than unit tests.
```java
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


        assertThat(result.getContent()).isEqualTo("Hello, testing!");
    }
}
```
## Integration tests using restTemplate
Rest template allow us to simulate a request coming from the external world of our application.
for this we are going to use the annotation `@LocalServerPort` to know which port our application is running locally,
this is because spring will try to allocate a randomPort this is a best practice,
in case you want to run test cases in parallel.

restTemplate is a class that allow us to make calls to our web application and get the response in a serialised object.
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingControllerExternalIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        //Arrange
        String greetName = "testing";
        String requestUrl = "http://localhost:" + port + "/greeting?name=" + greetName;

        //Act
        Greeting result = restTemplate.getForObject(requestUrl, Greeting.class);

        //Assert
        assertThat(result.getContent()).isEqualTo("Hello, testing!");
    }
}
```
## Assignment:
- Create an integration test for the statistics controller :)

## API Clients using retrofit
we would like to update our application to be able to include the weather information when we greet.
```
Hello World!, the weather today is: Sunny
```
for this we would like to integrate a weather API for it using the following endpoint:
```curl
https://fcc-weather-api.glitch.me/api/current?lat=52.5067614&lon=13.2846504
```
this api receive a location express by its `lat` and `lon` in the example above Berlin. and returns a json object like:
```json
{
    "coord": {
        "lon": 13.2847,
        "lat": 52.5068
    },
    "weather": [
        {
            "id": 804,
            "main": "Clouds",
            "description": "overcast clouds",
            "icon": "https://cdn.glitch.com/6e8889e5-7a72-48f0-a061-863548450de5%2F04n.png?1499366020983"
        }
    ],
    "base": "stations",
    "main": {
        "temp": 7.75,
        "feels_like": 5.46,
        "temp_min": 5.59,
        "temp_max": 9.95,
        "pressure": 996,
        "humidity": 59
    },
    "visibility": 10000,
    "wind": {
        "speed": 3.58,
        "deg": 320
    },
    "clouds": {
        "all": 99
    },
    "dt": 1648581053,
    "sys": {
        "type": 2,
        "id": 2018549,
        "country": "DE",
        "sunrise": 1648529316,
        "sunset": 1648575271
    },
    "timezone": 7200,
    "id": 7290251,
    "name": "Berlin Wilmersdorf",
    "cod": 200
}
```

we would like to use Retrofit which is a known library to get data from this api and include it into our API
1. Include Retrofit dependencies to your `pom.xml` file:
```xml
<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>retrofit</artifactId>
    <version>2.9.0</version>
</dependency>
<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>converter-jackson</artifactId>
    <version>2.9.0</version>
</dependency>
```
2. Add Retrotif configuration by creating a new Java class call `WeatherApiConfig` inside the dir `src/main/java/com/redi/client`
```java
@Configuration
public class WeatherApiConfig {

    private static String BASE_URL = "https://fcc-weather-api.glitch.me/api/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    @Bean
    public WeatherApi weatherApi() {
        return retrofit.create(WeatherApi.class);
    }
}
```
3. add the domain classes for the api response: `WeatherResponse` and `Weather` to get the response from the Weather API as follow:

WeatherResponse class:
```java
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    String name;
    @JsonProperty("weather")
    List<Weather> weathers;

    public WeatherResponse() {
    }

    public WeatherResponse(String name, List<Weather> weathers) {
        this.name = name;
        this.weathers = weathers;
    }

    public String getName() {
        return name;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }
}
```
Weather class:
```java
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    String main;

    public Weather() {
    }

    public Weather(String main) {
        this.main = main;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
```
4. Define the weather api as follows:
```java
public interface WeatherApi {
    @GET("current")
    Call<WeatherResponse> getWeather(@Query("lat") Double lat, @Query("lon") Double lon);
}
```
5. create a new service to make use of the WeatherApi call `WeatherService`
```java
@Service
public class WeatherService {

    private static Double BERLIN_LAT = 52.5067614;

    private static Double BERLIN_LON = 13.2846504;

    private static String DEFAULT_RESPONSE = "very good";

    private WeatherApi weatherApi;

    @Autowired
    public WeatherService(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public String getWeather() {
        try {
            Response<WeatherResponse> response = weatherApi.getWeather(BERLIN_LAT, BERLIN_LON).execute();
            if(response.isSuccessful()) {
                WeatherResponse weatherResponse = response.body();
                Weather weather = weatherResponse.getWeathers().get(0);
                if(weather != null) {
                    return weather.getMain();
                } else {
                    return DEFAULT_RESPONSE;
                }
            } else {
                System.out.println("there was an error" + response.errorBody());
                return DEFAULT_RESPONSE;
            }

        } catch (Exception e) {
            return DEFAULT_RESPONSE;
        }
    }
}
```
6. autowire the new service into the GreetingService and use it in our greet method:
```java
public class GreetingService {
    //..
    
    private WeatherService weatherService;

    @Autowired
    public GreetingService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public Greeting greet(String name) {
        validateGreetSize(name);
        storeGreetStatistic(name);
        return new Greeting(counter.incrementAndGet(), "Hello, " + name + "!, today the weather is: " + weatherService.getWeather());
    }
}
```
7. after this change many test cases will start failing make sure to fix them.

Assigment:
1. Create the Unit tests for the WeatherService
2. Integrate the https://www.boredapi.com/api/activity using retrofit to give a random activity in the response of the greeting API.
   the response should be something like:
```
Hello,World! today the weather is: sunny, also try out this activity: Make home ice cream.
```
3. How can we mock an external dependency using testcontainers and mock servers?
4. Mock the response of the weather API so that you can test sunny and rainy days.
