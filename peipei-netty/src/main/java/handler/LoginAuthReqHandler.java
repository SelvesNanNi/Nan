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
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当客户端跟服务端TCP三次握手成功之后，由客户端构造握手请求消息发送给服务端
        NettyMessage message = NettyMessage.buildNettyMessage(MessageType.LOGIN_REQ);
        log.info("发送握手消息`msg={}", message);
        ctx.writeAndFlush(message);
    }

    /**
     * 握手请求发送之后，按照协议规范，服务端需要返回握手应答消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        NettyMessage message = (NettyMessage) msg;
        if (MessageType.checkMessageType(message, MessageType.LOGIN_RESP)) {
            //如果是握手应答消息，需要判断是否认证成功
            byte loginResult = (Byte) message.getBody();
            if (loginResult != (byte) 0) {
                // 如果是握手应答消息，则对应答结果进行判断，如果非0，说明认证失败，关闭链路，重新发起连接。
                // 握手失败，关闭连接
                ctx.close();
            } else {
                log.info("Login success`message={} ", message);
                ctx.fireChannelRead(msg);
            }
        } else {
            // 如果不是，直接透传给后面的ChannelHandler进行处理；
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

