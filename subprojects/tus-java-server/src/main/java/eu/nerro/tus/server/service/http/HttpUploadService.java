package eu.nerro.tus.server.service.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;

import eu.nerro.tus.server.service.Service;

import static io.netty.channel.ChannelOption.*;
import static org.slf4j.LoggerFactory.getLogger;

public class HttpUploadService implements Service {

  private static final Logger LOG = getLogger(HttpUploadService.class);

  private static final String SERVICE_NAME = "HTTP Upload Service";
  private static final int DEFAULT_PORT = 9797;

  private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
  private EventLoopGroup workerGroup = new NioEventLoopGroup();

  @Override
  public void run() {
    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup, workerGroup)
               .channel(NioServerSocketChannel.class)
               .childHandler(new HttpUploadServerInitializer())
               .option(SO_BACKLOG, 128)
               .childOption(SO_KEEPALIVE, true)
               .childOption(TCP_NODELAY, true);

      Channel channel = bootstrap.bind(DEFAULT_PORT).sync().channel();

      LOG.info("'{}' bound on {}", SERVICE_NAME, channel.localAddress());

      channel.closeFuture().sync();
    } catch (Exception e) {
      LOG.error("Could not start '{}'", SERVICE_NAME, e);
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  @Override
  public void stop() {
    LOG.info("Stopping '{}'", SERVICE_NAME);

    bossGroup.shutdownGracefully();
    workerGroup.shutdownGracefully();

    LOG.info("'{}' stopped", SERVICE_NAME);
  }
}
