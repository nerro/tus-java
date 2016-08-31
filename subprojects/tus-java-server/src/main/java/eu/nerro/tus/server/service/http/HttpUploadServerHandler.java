package eu.nerro.tus.server.service.http;

import org.slf4j.Logger;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import eu.nerro.tus.server.service.http.response.OptionsResponse;

import static org.slf4j.LoggerFactory.getLogger;

class HttpUploadServerHandler extends SimpleChannelInboundHandler<HttpRequest> {

  private static final Logger LOG = getLogger(HttpUploadServerHandler.class);

  @Override
  protected void channelRead0(final ChannelHandlerContext ctx, final HttpRequest request) throws Exception {
    HttpResponse response = buildDefaultHttpResponse();

    if (HttpMethod.OPTIONS.equals(request.method())) {
      response = new OptionsResponse.Builder(request).build();
    }

    ChannelFuture future = ctx.write(response);
    future.addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext ctx, Throwable cause) throws Exception {
    LOG.error("Caught exception in channel '" + ctx.name() + "'", cause);
    ctx.channel().close();
  }

  /**
   * Builds HTTP response without headers and body and with status code {@code 403}.
   */
  private HttpResponse buildDefaultHttpResponse() {
    return new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN, false);
  }
}
