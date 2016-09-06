package eu.nerro.tus.server.store.file;

import eu.nerro.tus.server.store.FileInfo;
import eu.nerro.tus.server.store.Store;

public class FileStore implements Store {

  public FileStore() {
  }

  @Override
  public String createNewUpload(FileInfo fileInfo) {
    return null;
  }

  @Override
  public long writeChunk(String id, long offset) {
    return 0;
  }

  @Override
  public FileInfo getFileInfo(String id) {
    return null;
  }
}
