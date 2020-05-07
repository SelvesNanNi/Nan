package server.codec;

import common.SimpleRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public class SimpleProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        SimpleRequestMessage requestMessage = new SimpleRequestMessage();
        requestMessage.decode(msg);

        out.add(requestMessage);
    }
}
