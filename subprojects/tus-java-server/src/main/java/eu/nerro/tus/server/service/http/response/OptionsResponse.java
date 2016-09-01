package eu.nerro.tus.server.service.http.response;

import io.netty.handler.codec.http.*;

import eu.nerro.tus.server.service.http.HttpHeaders;

public class OptionsResponse {

  private HttpResponse response;

  private OptionsResponse(Builder builder) {
    if (builder.request == null) {
      throw new IllegalArgumentException("OPTIONS request must not be null");
    }

    response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT, false);
    response.headers().add(HttpHeaders.TUS_VERSION, "1.0.0");
    response.headers().add(HttpHeaders.X_CONTENT_TYPE_OPTIONS, "nosniff");
  }

  public static final class Builder {

    private final HttpRequest request;

    public Builder(HttpRequest request) {
      this.request = request;
    }

    public HttpResponse build() {
      return new OptionsResponse(this).response;
    }
  }
}
