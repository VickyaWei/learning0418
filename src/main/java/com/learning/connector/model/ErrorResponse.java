package com.learning.connector.model;

import java.time.LocalDateTime;

/**
 * @author vickyaa
 * @date 5/6/25
 * @file ErrorResponse
 */
public class ErrorResponse {
  private LocalDateTime timestamp;
  private String message;
  private String details;

  public ErrorResponse(LocalDateTime timestamp, String details, String message) {
    this.timestamp = timestamp;
    this.details = details;
    this.message = message;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}
