package com.learning.connector.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vickyaa
 * @date 5/16/25
 * @file SplunkLogForwarder
 */
@Component
@EnableScheduling
public class SplunkLogForwarder {
  private static final Logger logger = LoggerFactory.getLogger(SplunkLogForwarder.class);
  private long lastPosition = 0;

  @Value("${splunk.hec.url}")
  private String splunkHecUrl;

  @Value("${splunk.hec.token}")
  private String splunkHecToken;

  @Value("${splunk.log.file:logs/splunk-formatted.log}")
  private String logFile;

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(Paths.get("logs"));
    } catch (IOException e) {
      logger.error("Failed to create logs directory", e);
    }
  }

  @Scheduled(fixedDelay = 5000) // Run every 5 seconds
  public void forwardLogsToSplunk() {
    try {
      Path path = Paths.get(logFile);
      if (!Files.exists(path)) {
        return;
      }

      try (RandomAccessFile raf = new RandomAccessFile(logFile, "r")) {
        raf.seek(lastPosition);
        String line;

        while ((line = raf.readLine()) != null) {
          sendToSplunk(line);
        }

        lastPosition = raf.getFilePointer();
      }
    } catch (Exception e) {
      logger.error("Error forwarding logs to Splunk", e);
    }
  }

  private void sendToSplunk(String logLine) {
    try {
      if (logLine == null || logLine.trim().isEmpty()) {
        return;
      }

      // Create RestTemplate with proper error handling
      RestTemplate restTemplate = new RestTemplate();

      // Prepare headers with authorization
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Splunk " + splunkHecToken);
      headers.setContentType(MediaType.APPLICATION_JSON);

      // Create the request entity
      ObjectMapper mapper = new ObjectMapper();
      JsonNode logData = mapper.readTree(logLine);

      Map<String, Object> eventData = new HashMap<>();
      eventData.put("event", logData);

      HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(eventData, headers);

      // Send to Splunk
      ResponseEntity<String> response = restTemplate.exchange(
          splunkHecUrl,
          HttpMethod.POST,
          requestEntity,
          String.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        logger.debug("Successfully sent log to Splunk");
      } else {
        logger.warn("Failed to send log to Splunk: {}", response.getStatusCode());
      }
    } catch (Exception e) {
      logger.error("Error sending log to Splunk", e);
    }
  }
}