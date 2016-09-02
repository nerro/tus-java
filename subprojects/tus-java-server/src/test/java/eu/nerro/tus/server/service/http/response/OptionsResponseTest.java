package eu.nerro.tus.server.service.http.response;

import io.netty.handler.codec.http.*;
import org.junit.Test;

import eu.nerro.tus.server.service.http.HttpHeaders;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class OptionsResponseTest {

  @Test(expected = IllegalArgumentException.class)
  public void build_shouldThrowIAE_ifRequestIsNull() {
    new OptionsResponse.Builder(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void build_shouldThrowIAE_ifRequestMethodIsNotOPTIONS() {
    HttpRequest postRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "localhost");

    new OptionsResponse.Builder(postRequest).build();
  }

  @Test
  public void build_shouldReturn204_onSuccess() {
    HttpRequest request = createOptionsRequest();

    HttpResponse response = new OptionsResponse.Builder(request).build();

    assertThat(response.status(), equalTo(HttpResponseStatus.NO_CONTENT));
  }

  @Test
  public void build_shouldContainTusVersionHeader() {
    HttpRequest request = createOptionsRequest();

    HttpResponse response = new OptionsResponse.Builder(request).build();
    String headerTusVersion = response.headers().get(HttpHeaders.TUS_VERSION);

    assertThat(headerTusVersion, is(notNullValue()));
  }

  @Test
  public void build_shouldHaveNosniffValue_forXContentTypeOptionsHeader() {
    HttpRequest request = createOptionsRequest();

    HttpResponse response = new OptionsResponse.Builder(request).build();
    String headerXContentTypeOptions = response.headers().get(HttpHeaders.X_CONTENT_TYPE_OPTIONS);

    assertThat(headerXContentTypeOptions, is(notNullValue()));
    assertThat(headerXContentTypeOptions, equalTo("nosniff"));
  }

  private HttpRequest createOptionsRequest() {
    return new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.OPTIONS, "localhost");
  }
}
