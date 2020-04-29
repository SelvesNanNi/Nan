package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import protocol.Header;
import protocol.NettyMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Selves
 * @Date 2020/4/18
 */
@Slf4j
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private MarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        log.info("准备解码");
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        //对于业务解码器来说，调用父类LengthFieldBasedFrameDecoder的解码方法后，返回的就是整包消息或者为空，
        //如果为空说明是个半包消息，直接返回继续由I/O线程读取后续的码流
        if (frame == null) {
            return null;
        }
        int pre = in.readerIndex();
        in.readerIndex(0);
        Header header = Header.builder()
                .crcCode(in.readInt())
                .length(in.readInt())
                .sessionID(in.readLong())
                .type(in.readByte())
                .priority(in.readByte())
                .build();
        log.info("解析请求头完毕`header={}", header);
        NettyMessage message = NettyMessage.builder().build();
        int size = in.readInt();
        if (size > 0) {
            Map<String, Object> attachment = new HashMap<>(size);
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = in.readInt();
                keyArray = new byte[keySize];
                in.readBytes(keyArray);
                key = new String(keyArray, "UTF-8");
                attachment.put(key, marshallingDecoder.decode(in));
            }
            keyArray = null;
            key = null;
            header.setAttachment(attachment);
        }
        message.setHeader(header);
        if (in.readableBytes() > 4) {
            message.setBody(marshallingDecoder.decode(in));
        }
        in.readerIndex(pre);
        message.setHeader(header);
        return message;

    }
}
