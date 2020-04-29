package handler;

import enums.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import protocol.NettyMessage;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Selves
 * @Date 2020/4/18
 */
@Slf4j
public class LoginAuthRespHandler extends SimpleChannelInboundHandler {

    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private String[] whiteList = {"127.0.0.1"};

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
//        log.info("LoginAuthRespHandler 激活-2");
//
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        NettyMessage message = (NettyMessage) msg;
//        log.info("认证消息,msg={}", message);
//        // 处理握手请求消息，其他消息透传
//        if (MessageType.checkMessageType(message, MessageType.LOGIN_REQ)) {
//            String nodeIndex = ctx.channel().remoteAddress().toString();
//            NettyMessage loginResp = null;
//            // 重复登录保护，拒绝重复连接
//            if (nodeCheck.containsKey(nodeIndex)) {
//                loginResp = NettyMessage.buildNettyMessage((byte) -1, MessageType.LOGIN_RESP);
//            } else {
//                //IP认证白名单列表
//                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
//                String ip = address.getAddress().getHostAddress();
//                boolean isOK = checkAuth(ip);
//                //通过buildResponse构造握手应答消息返回给客户端
//                loginResp = isOK ? NettyMessage.buildNettyMessage((byte) 0, MessageType.LOGIN_RESP) : NettyMessage.buildNettyMessage((byte) -1, MessageType.LOGIN_RESP);
//                if (isOK) {
//                    nodeCheck.put(nodeIndex, true);
//                }
//            }
//            log.info("The login response is : [{}]` ,body [{}]", loginResp, loginResp.getBody());
//            ctx.writeAndFlush(loginResp);
//        } else {
//            ctx.fireChannelRead(msg);
//        }
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        log.info("认证消息,msg={}", message);
        // 处理握手请求消息，其他消息透传
        if (MessageType.checkMessageType(message, MessageType.LOGIN_REQ)) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            // 重复登录保护，拒绝重复连接
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = NettyMessage.buildNettyMessage((byte) -1, MessageType.LOGIN_RESP);
            } else {
                //IP认证白名单列表
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = checkAuth(ip);
                //通过buildResponse构造握手应答消息返回给客户端
                loginResp = isOK ? NettyMessage.buildNettyMessage((byte) 0, MessageType.LOGIN_RESP) : NettyMessage.buildNettyMessage((byte) -1, MessageType.LOGIN_RESP);
                if (isOK) {
                    nodeCheck.put(nodeIndex, true);
                }
            }
            log.info("The login response is : [{}]` ,body [{}]", loginResp, loginResp.getBody());
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理握手请求时异常`occur exception`ctx=", cause);
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 检查白名单列表
     *
     * @param clientIp
     * @return
     */
    private boolean checkAuth(String clientIp) {
        for (String WIP : whiteList) {
            if (WIP.equals(clientIp)) {
                return true;
            }
        }
        return false;
    }
}
