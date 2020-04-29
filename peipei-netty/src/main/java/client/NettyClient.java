package client;

import codec.NettyMessageDecoder;
import codec.NettyMessageEncoder;
import handler.HeartBeatReqHandler;
import handler.LoginAuthReqHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Selves
 * @Date 2020/4/18
 */
@Slf4j
public class NettyClient {
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        new NettyClient().connect("127.0.0.1", 8007);
    }

    public void connect(String host, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("NettyMessageDecoder", new NettyMessageDecoder(1024 * 1024, 4, 4));
                            ch.pipeline().addLast("NettyMessageEncoder", new NettyMessageEncoder());
                            ch.pipeline().addLast("ReadTimeoutHandler", new ReadTimeoutHandler(5));
                            ch.pipeline().addLast("LoginAuthReqHandler", new LoginAuthReqHandler());
                            ch.pipeline().addLast("HeartBeatReqHandler", new HeartBeatReqHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
            //所有资源释放完成后，清空资源，再次发起重连操作
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        TimeUnit.SECONDS.sleep(5);
//                        connect(host, port);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        }
    }

}
