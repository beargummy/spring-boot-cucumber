package com.beargummy.example.cucumber;


import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class GreetingsResourceSteps {

  @Autowired
  private TestRestTemplate restTemplate;

  private String caller;

  private ResponseEntity<String> response;

  @Given("I use the caller (.*)")
  public void useCaller(String caller) {
    this.caller = caller;
  }

  @When("I request a greeting")
  public void requestGreeting() {
    response = restTemplate
        .exchange("/greetings/{caller}", HttpMethod.GET, null, String.class, caller);
  }

  @Then("I should get a response with HTTP status code (.*)")
  public void shouldGetResponseWithHttpStatusCode(int statusCode) {
    assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
  }

  @And("The response should contain the message (.*)")
  public void theResponseShouldContainTheMessage(String message) {
    assertThat(response.getBody()).isEqualTo(message);
  }

}