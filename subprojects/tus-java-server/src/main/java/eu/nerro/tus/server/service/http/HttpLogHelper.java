package eu.nerro.tus.server.service.http;

import io.netty.handler.codec.http.HttpRequest;

/**
 * Logging helper for HTTP requests and responses.
 */
final class HttpLogHelper {

  /**
   * Builds one line log entry for given HTTP request.
   * <br>
   * <i>Note:</i> This method is used for log level {@code INFO} or {@code WARN}.
   */
  static String buildSimpleLogEntryFor(HttpRequest request) {
    return new StringBuilder().append("{")
                              .append("\"method\":\"").append(request.method()).append("\",")
                              .append("\"protocol_version\":\"").append(request.protocolVersion()).append("\",")
                              .append("\"host\":\"").append(request.headers().get(HttpHeaders.HOST)).append("\",")
                              .append("\"uri\":\"").append(request.uri()).append("\"")
                              .append("}")
                              .toString();
  }
}
