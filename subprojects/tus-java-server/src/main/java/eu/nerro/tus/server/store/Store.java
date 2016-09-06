package eu.nerro.tus.server.store;

public interface Store {

  String createNewUpload(FileInfo fileInfo);

  long writeChunk(String id, long offset);

  FileInfo getFileInfo(String id);
}
