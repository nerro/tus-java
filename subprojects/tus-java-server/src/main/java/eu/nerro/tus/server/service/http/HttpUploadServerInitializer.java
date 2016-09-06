package eu.nerro.tus.server.service.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import eu.nerro.tus.server.store.Store;

class HttpUploadServerInitializer extends ChannelInitializer<SocketChannel> {

  private Store store;

  HttpUploadServerInitializer(Store store) {
    this.store = store;
  }

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    ChannelPipeline pipeline = socketChannel.pipeline();

    pipeline.addLast(new HttpRequestDecoder());
    pipeline.addLast(new HttpResponseEncoder());
    pipeline.addLast(new HttpUploadServerHandler(store));
  }
}
