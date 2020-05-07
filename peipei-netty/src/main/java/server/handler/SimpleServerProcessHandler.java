package server.handler;

import common.SimpleRequestMessage;
import common.SimpleResponseMessage;
import common.op.SimpleOp;
import common.op.SimpleOpResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Selves
 * @Date 2020/4/30
 */
@Slf4j
public class SimpleServerProcessHandler extends SimpleChannelInboundHandler<SimpleRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleRequestMessage msg) throws Exception {
        SimpleOp messageBody = msg.getMessageBody();
        SimpleOpResult opResult = messageBody.execute();

        SimpleResponseMessage responseMessage = new SimpleResponseMessage();
        responseMessage.setHeader(msg.getHeader());
        responseMessage.setMessageBody(opResult);

        ctx.writeAndFlush(responseMessage);
    }
}
