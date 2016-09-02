package eu.nerro.tus.server.service.http.response;

import io.netty.handler.codec.http.*;
import org.junit.Test;

import eu.nerro.tus.server.service.http.HttpHeaders;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class HeadResponseTest {

  @Test(expected = IllegalArgumentException.class)
  public void build_shouldThrowIAE_ifRequestIsNull() {
    new HeadResponse.Builder(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void build_shouldThrowIAE_ifRequestMethodIsNotHEAD() {
    HttpRequest postRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "localhost");

    new HeadResponse.Builder(postRequest).build();
  }

  @Test
  public void build_shouldReturn200_onSuccess() {
    HttpRequest request = createHeadRequest();

    HttpResponse response = new HeadResponse.Builder(request).build();

    assertThat(response.status(), equalTo(HttpResponseStatus.OK));
  }

  @Test
  public void build_shouldHaveNoStoreValue_forCacheControlHeader() {
    HttpRequest request = createHeadRequest();

    HttpResponse response = new HeadResponse.Builder(request).build();
    String headerCacheControl = response.headers().get(HttpHeaders.CACHE_CONTROL);

    assertThat(headerCacheControl, is(notNullValue()));
    assertThat(headerCacheControl, equalTo("no-store"));
  }

  private HttpRequest createHeadRequest() {
    return new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.HEAD, "localhost");
  }
}
