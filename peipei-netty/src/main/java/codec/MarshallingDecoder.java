package codec;

import buf.ChannelBufferByteInput;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * @author Selves
 * @Date 2020/4/18
 */
public class MarshallingDecoder {
    private final Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodecFactory.buildMarshallingDecoder();
    }

    protected Object decode(ByteBuf in) throws Exception {
        //读取消息长度字段的值
        int objectSize = in.readInt();
        //切割Buffer，从已读下标处开始，后移n个字节单位，n为消息长度的值
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        ByteInput input = new ChannelBufferByteInput(buf);
        try {
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            //更新ByteBuf的已读下标
            in.readerIndex(in.readerIndex() + objectSize);
            return obj;
        } finally {
            unmarshaller.close();
        }
    }
}
