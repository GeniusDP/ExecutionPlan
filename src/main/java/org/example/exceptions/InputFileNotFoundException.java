package org.example.exceptions;

public class InputFileNotFoundException extends RuntimeException {

  public InputFileNotFoundException() {}

  public InputFileNotFoundException(String message) {
    super(message);
  }

  public InputFileNotFoundException(Throwable cause) {
    super(cause);
  }
}
