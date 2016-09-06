package eu.nerro.tus.server.store;

public enum Datastore {
  FILESTORE("filestore"),
  UNKNOWN("unknown");

  private final String value;

  Datastore(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  /**
   * Returns the {@code Datastore} constant of the specified enum type with the specified name.
   * <p>
   * Note: if no constant could be found, then {@link #UNKNOWN} will be returned.
   */
  public static Datastore fromValue(String value) {
    if (value != null) {
      for (Datastore datastore : values()) {
        if (datastore.value.equals(value)) {
          return datastore;
        }
      }
    }

    return UNKNOWN;
  }
}
