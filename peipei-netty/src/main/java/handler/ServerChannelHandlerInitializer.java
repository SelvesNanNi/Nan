package handler;

import codec.NettyMessageDecoder;
import codec.NettyMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Selves
 * @Date 2020/4/18
 */
@Slf4j
public class ServerChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("NettyMessageDecoder", new NettyMessageDecoder(1024 * 1024, 4, 4));
        pipeline.addLast("NettyMessageEncoder", new NettyMessageEncoder());
        pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(5));
        pipeline.addLast("LoginAuthRespHandler", new LoginAuthRespHandler());
        pipeline.addLast("HeartBeatHandler", new HeartBeatRespHandler());
        for (String name : pipeline.names()) {
            log.info("已加载：{}",name);
        }
    }
}
