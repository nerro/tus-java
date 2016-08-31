package eu.nerro.tus.server.service.http;

import io.netty.util.AsciiString;

public final class HttpHeaders {

  public static final CharSequence TUS_VERSION = new AsciiString("Tus-Version");

  private HttpHeaders() {
  }
}
