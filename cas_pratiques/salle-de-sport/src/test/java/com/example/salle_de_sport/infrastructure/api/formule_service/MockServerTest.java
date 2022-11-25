package com.example.salle_de_sport.infrastructure.api.formule_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public abstract class MockServerTest {

  protected MockWebServer mockServer;

  @BeforeEach
  public void startMockServer() throws IOException {
    mockServer = new MockWebServer();
    mockServer.start();
  }

  @AfterEach
  public void stopMockServer() throws IOException {
    mockServer.shutdown();
  }

  protected void renvoyerReponse (int code, Object body) throws JsonProcessingException {
    MockResponse mockResponse = new MockResponse();
    mockResponse.setResponseCode(code);
    if (body != null) {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      mockResponse.setBody(objectMapper.writeValueAsString(body));
    }
    mockResponse.setHeader("Content-Type", "application/json");
    mockServer.enqueue(mockResponse);
  }
}
