package eu.nerro.tus.server.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ShutdownHookService implements Runnable {

  private static final Logger LOG = getLogger(ShutdownHookService.class);

  private final List<Service> services;

  public ShutdownHookService(List<Service> services) {
    this.services = services;
  }

  @Override
  public void run() {
    LOG.info("Shutdown has been requested");
    final long startTime = System.nanoTime();

    services.forEach(Service::stop);

    final long endTime = System.nanoTime();
    LOG.info("TUS Java server stopped in {} ms", TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS));
  }
}
