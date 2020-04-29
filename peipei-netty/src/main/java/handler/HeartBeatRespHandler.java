package handler;

import enums.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import protocol.NettyMessage;

/**
 * @author Selves
 * @Date 2020/4/18
 */
@Slf4j
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 返回心跳应答消息
        if (MessageType.checkMessageType(message, MessageType.HEARTBEAT_REQ)) {
            log.info("Receive client heart beat message : ---> {}", message);
            NettyMessage heartBeat = NettyMessage.buildNettyMessage(MessageType.HEARTBEAT_RESP);
            log.info("Send heart beat response message to client : ---> {} ", heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

}
