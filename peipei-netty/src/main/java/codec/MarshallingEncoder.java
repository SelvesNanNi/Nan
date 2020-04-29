package codec;

import buf.ChannelBufferByteOutput;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author Selves
 * @Date 2020/4/18
 */
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;

    public MarshallingEncoder() throws IOException, IOException {
        marshaller = MarshallingCodecFactory.buildMarshallingEncoder();

    }

    /**
     * 消息编码
     * out:1 2 3 4
     * lengthPos = 4，从0开始
     * 预占消息长度字段，4字节
     * 1 2 3 4 ？
     * 写消息
     * msg: 5 6 7 8
     * out`: 1 2 3 4 ？ 5 6 7 8
     * 更新消息长度字段的值
     * writerIndex = 12
     * 实际消息长度=总长度-初始长度-消息长度字段的占用字节数=12-4-4=4
     *
     * @param msg
     * @param out
     * @throws Exception
     */
    protected void encode(Object msg, ByteBuf out) throws Exception {
        try {
            //可写下标
            int lengthPos = out.writerIndex();
            //预留消息长度,4字节
            out.writeBytes(LENGTH_PLACEHOLDER);
            ByteOutput output = new ChannelBufferByteOutput(out);
            //写消息
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            //更新消息长度字段，值=当前可写下标-初始可写下标
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }
}
