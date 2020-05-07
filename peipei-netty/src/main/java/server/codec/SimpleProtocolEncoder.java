package server.codec;

import common.SimpleRequestMessage;
import common.SimpleResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public class SimpleProtocolEncoder extends MessageToMessageEncoder<SimpleResponseMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SimpleResponseMessage msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        msg.encode(buffer);

        out.add(buffer);
    }
}
