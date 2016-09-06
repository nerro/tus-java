package eu.nerro.tus.server.configuration;

public class ConfigVarHelper {

  public static String getEnvValue(ConfigVar configVar) {
    return System.getenv(configVar.name());
  }
}
