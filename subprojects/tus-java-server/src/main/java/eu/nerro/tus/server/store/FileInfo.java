package eu.nerro.tus.server.store;

import java.util.Map;

public class FileInfo {
  private String id;
  private long size;
  private long offset;
  private Map<String, String> metadata;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
