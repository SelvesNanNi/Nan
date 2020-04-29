package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import protocol.NettyMessage;

import java.io.IOException;
import java.util.Map;

/**
 * @author Selves
 * @Date 2020/4/18
 */
@Slf4j
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf sendBuf) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }
        sendBuf.writeInt(msg.getHeader().getCrcCode())
                .writeInt(msg.getHeader().getLength())
                .writeLong(msg.getHeader().getSessionID())
                .writeByte(msg.getHeader().getType())
                .writeByte(msg.getHeader().getPriority())
                .writeInt(msg.getHeader().getAttachment().size());
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes();
            value = param.getValue();
            sendBuf.writeInt(keyArray.length)
                    .writeBytes(keyArray);
            marshallingEncoder.encode(value,sendBuf);
        }
        key = null;
        keyArray = null;
        value = null;
        if (msg.getBody() != null) {
            marshallingEncoder.encode(msg.getBody(), sendBuf);
        } else {
            sendBuf.writeInt(0);
        }
        //设置消息长度 即Length字段
        sendBuf.setInt(4, sendBuf.readableBytes()-8);

    }

//    @Override
//    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
//        if (msg == null || msg.getHeader() == null) {
//            throw new Exception("The encode message is null");
//        }
//        ByteBuf sendBuf = Unpooled.buffer();
//        sendBuf.writeInt(msg.getHeader().getCrcCode())
//                .writeInt(msg.getHeader().getLength())
//                .writeLong(msg.getHeader().getSessionID())
//                .writeByte(msg.getHeader().getType())
//                .writeByte(msg.getHeader().getPriority())
//                .writeInt(msg.getHeader().getAttachment().size());
//        String key = null;
//        byte[] keyArray = null;
//        Object value = null;
//        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
//            key = param.getKey();
//            keyArray = key.getBytes();
//            value = param.getValue();
//            sendBuf.writeInt(keyArray.length)
//                    .writeBytes(keyArray);
//            marshallingEncoder.encode(value,sendBuf);
//        }
//        key = null;
//        keyArray = null;
//        value = null;
//        if (msg.getBody() != null) {
//            marshallingEncoder.encode(msg.getBody(), sendBuf);
//        } else {
//            sendBuf.writeInt(0);
//        }
//        //设置消息长度 即Length字段
//        sendBuf.setInt(4, sendBuf.readableBytes());
//
//        out.add(sendBuf);
//    }
}
