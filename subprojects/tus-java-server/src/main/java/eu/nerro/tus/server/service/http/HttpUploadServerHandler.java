package eu.nerro.tus.server.service.http;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;

import eu.nerro.tus.server.service.http.response.OptionsResponse;

import static eu.nerro.tus.server.service.http.HttpLogHelper.buildSimpleLogEntryFor;
import static org.slf4j.LoggerFactory.getLogger;

class HttpUploadServerHandler extends SimpleChannelInboundHandler<HttpRequest> {

  private static final Logger LOG = getLogger(HttpUploadServerHandler.class);

  @Override
  protected void channelRead0(final ChannelHandlerContext ctx, final HttpRequest request) throws Exception {
    HttpResponse response = null;

    if (HttpMethod.OPTIONS.equals(request.method())) {
      response = new OptionsResponse.Builder(request).build();
    }

    if (response != null) {
      ChannelFuture future = ctx.write(response);
      future.addListener(ChannelFutureListener.CLOSE);
    } else {
      LOG.warn("Unsupported http request: {}", buildSimpleLogEntryFor(request));
      ctx.channel().close();
    }
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
}
