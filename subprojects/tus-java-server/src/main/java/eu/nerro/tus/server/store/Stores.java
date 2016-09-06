package eu.nerro.tus.server.store;

import eu.nerro.tus.server.store.file.FileStore;

/**
 * Factory and utility methods for {@link Store}.
 */
public final class Stores {

  private Stores() {
  }

  public static Store newFileStore() {
    return new FileStore();
  }
}
