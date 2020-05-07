package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import server.codec.SimpleFrameDecoder;
import server.codec.SimpleFrameEncoder;
import server.codec.SimpleProtocolDecoder;
import server.codec.SimpleProtocolEncoder;
import server.handler.SimpleServerProcessHandler;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public class Server {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("boss"));
        NioEventLoopGroup workGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));
        try{
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .group(bossGroup,workGroup)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new SimpleFrameDecoder());
                            pipeline.addLast(new SimpleFrameEncoder());
                            pipeline.addLast(new SimpleProtocolDecoder());
                            pipeline.addLast(new SimpleProtocolEncoder());
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(new SimpleServerProcessHandler());

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
