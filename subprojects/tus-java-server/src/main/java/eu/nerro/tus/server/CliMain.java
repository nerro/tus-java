package eu.nerro.tus.server;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import eu.nerro.tus.server.configuration.ConfigVar;
import eu.nerro.tus.server.configuration.ConfigVarHelper;
import eu.nerro.tus.server.exception.TusExceptionHandler;
import eu.nerro.tus.server.service.Service;
import eu.nerro.tus.server.service.ShutdownHookService;
import eu.nerro.tus.server.service.http.HttpUploadService;
import eu.nerro.tus.server.store.Datastore;
import eu.nerro.tus.server.store.Store;
import eu.nerro.tus.server.store.Stores;

import static org.slf4j.LoggerFactory.getLogger;

public class CliMain {

  private static final Logger LOG = getLogger(CliMain.class);

  public static void main(String[] args) {
    LOG.info("TUS Java server starting");
    final long startTime = System.nanoTime();

    // setup exception handler to handle all uncaught (runtime) exceptions
    Thread.setDefaultUncaughtExceptionHandler(new TusExceptionHandler());

    // initialize upload store mechanism
    final Store store;
    final Datastore datastore = getConfiguredDatastore();
    switch (datastore) {
      case FILESTORE:
        store = Stores.newFileStore();
        break;

      default:
        throw new IllegalArgumentException("No valid datastore defined: '" + datastore + "'");
    }

    final Service httpUploadService = new HttpUploadService(store);
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.execute(httpUploadService);

    Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookService(Collections.singletonList(httpUploadService))));

    final long endTime = System.nanoTime();
    LOG.info("TUS Java server started in {} ms", TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS));
  }

  /**
   * Reads the config {@link ConfigVar#DATASTORE} from environment variables.
   * <p>
   * <i>Note:</i> if the variable is not defined or could not be parsed,
   * then {@link Datastore#FILESTORE} will be returned as default value.
   */
  private static Datastore getConfiguredDatastore() {
    final String datastoreConfigVar = ConfigVarHelper.getEnvValue(ConfigVar.DATASTORE);
    if (datastoreConfigVar == null || datastoreConfigVar.isEmpty()) {
      LOG.warn("Configuration variable '{}' is not defined, fallback to '{}'", ConfigVar.DATASTORE, Datastore.FILESTORE);
      return Datastore.FILESTORE;
    }

    Datastore datastore = Datastore.fromValue(datastoreConfigVar);

    if (Datastore.UNKNOWN.equals(datastore)) {
      LOG.warn("Environment variable '{}={}' could not be parsed, fallback to '{}'", ConfigVar.DATASTORE, datastoreConfigVar, Datastore.FILESTORE);
      datastore = Datastore.FILESTORE;
    }

    return datastore;
  }
}
