package eu.nerro.tus.server;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import eu.nerro.tus.server.exception.TusExceptionHandler;
import eu.nerro.tus.server.service.Service;
import eu.nerro.tus.server.service.ShutdownHookService;
import eu.nerro.tus.server.service.http.HttpUploadService;

import static org.slf4j.LoggerFactory.getLogger;

public class CliMain {

  private static final Logger LOG = getLogger(CliMain.class);

  public static void main(String[] args) {
    LOG.info("TUS Java server starting");
    final long startTime = System.nanoTime();

    // setup exception handler to handle all uncaught (runtime) exceptions
    Thread.setDefaultUncaughtExceptionHandler(new TusExceptionHandler());

    final Service httpUploadService = new HttpUploadService();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.execute(httpUploadService);

    Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookService(Collections.singletonList(httpUploadService))));

    final long endTime = System.nanoTime();
    LOG.info("TUS Java server started in {} ms", TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS));
  }
}
