package eu.nerro.tus.server.exception;

public class TusException extends RuntimeException {

  public TusException(String message) {
    super(message);
  }

  public TusException(String message, Throwable cause) {
    super(message, cause);
  }
}
