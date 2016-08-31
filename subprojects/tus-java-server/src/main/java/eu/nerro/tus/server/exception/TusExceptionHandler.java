package eu.nerro.tus.server.exception;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Default handler invoked when {@code TUS Java server} abruptly terminates due to an uncaught exception.
 */
public class TusExceptionHandler implements Thread.UncaughtExceptionHandler {

  private static final Logger LOG = getLogger(TusExceptionHandler.class);

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    LOG.error("Uncaught exception in thread '" + t.getName() + "'", e);
  }
}
