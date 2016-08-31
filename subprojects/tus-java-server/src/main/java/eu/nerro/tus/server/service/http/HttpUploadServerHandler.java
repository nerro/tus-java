package eu.nerro.tus.server.service.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

class HttpUploadServerHandler extends SimpleChannelInboundHandler<HttpRequest> {

  private static final Logger LOG = getLogger(HttpUploadServerHandler.class);

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpRequest httpRequest) throws Exception {
    LOG.info(httpRequest.toString());
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    LOG.error("Caught exception in channel '" + ctx.name() + "'", cause);
    ctx.channel().close();
  }
}
