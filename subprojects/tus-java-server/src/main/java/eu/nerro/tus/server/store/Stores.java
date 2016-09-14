package eu.nerro.tus.server.store;

import org.slf4j.Logger;

import eu.nerro.tus.server.store.file.FileStore;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Factory and utility methods for {@link Store}.
 */
public final class Stores {

  private static final Logger LOG = getLogger(Stores.class);

  private Stores() {
  }

  public static Store newFileStore() {
    LOG.info("File store initializing");
    final long startTime = System.nanoTime();

    final FileStore fileStore = new FileStore();

    final long endTime = System.nanoTime();
    LOG.info("File store initialized in {} ms", MILLISECONDS.convert(endTime - startTime, NANOSECONDS));

    return fileStore;
  }
}
