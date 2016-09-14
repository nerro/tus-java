package eu.nerro.tus.server.store.file;

import org.junit.Test;

import eu.nerro.tus.server.store.Store;
import eu.nerro.tus.server.store.Stores;

public class FileStoreTest {

  @Test(expected = IllegalArgumentException.class)
  public void createNewUpload_shouldThrowIAE_ifFileInfoIsNull() {
    Store fileStore = Stores.newFileStore();
    fileStore.createNewUpload(null);
  }
}
