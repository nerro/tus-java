package eu.nerro.tus.server.service.http;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.AsciiString;

public final class HttpHeaders {

  public static final CharSequence HOST = HttpHeaderNames.HOST;
  public static final CharSequence CACHE_CONTROL = new AsciiString("Cache-Control");

  public static final CharSequence X_CONTENT_TYPE_OPTIONS = new AsciiString("X-Content-Type-Options");

  public static final CharSequence TUS_VERSION = new AsciiString("Tus-Version");

  private HttpHeaders() {
  }
}
