package eu.nerro.tus.server.service.http.response;

import org.junit.Test;

import io.netty.handler.codec.http.*;

import eu.nerro.tus.server.service.http.HttpHeaders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class OptionsResponseTest {

  @Test(expected = IllegalArgumentException.class)
  public void build_shouldThrowIAE_ifRequestIsNull() {
    new OptionsResponse.Builder(null).build();
  }

  @Test
  public void build_shouldReturn204_onSuccess() {
    HttpRequest request = createOptionsRequest();

    HttpResponse response = new OptionsResponse.Builder(request).build();

    assertThat(response.status(), equalTo(HttpResponseStatus.NO_CONTENT));
  }

  @Test
  public void bulid_shouldContainTusVersionHeader() {
    HttpRequest request = createOptionsRequest();

    HttpResponse response = new OptionsResponse.Builder(request).build();

    assertThat(response.headers().get(HttpHeaders.TUS_VERSION), notNullValue());
  }

  private HttpRequest createOptionsRequest() {
    return new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.OPTIONS, "localhost");
  }
}
