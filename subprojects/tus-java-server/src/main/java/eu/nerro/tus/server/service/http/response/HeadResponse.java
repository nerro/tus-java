package eu.nerro.tus.server.service.http.response;

import io.netty.handler.codec.http.*;

import eu.nerro.tus.server.service.http.HttpHeaders;

public class HeadResponse {

  private HttpResponse response;

  private HeadResponse(Builder builder) {
    if (builder.request == null) {
      throw new IllegalArgumentException("HEAD request must not be null");
    }
    if (!HttpMethod.HEAD.equals(builder.request.method())) {
      throw new IllegalArgumentException("Http request method must be HEAD");
    }

    response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, false);
    response.headers().add(HttpHeaders.CACHE_CONTROL, "no-store");
  }

  public static final class Builder {

    private final HttpRequest request;

    public Builder(HttpRequest request) {
      this.request = request;
    }

    public HttpResponse build() {
      return new HeadResponse(this).response;
    }
  }
}
