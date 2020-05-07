package common;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import util.GsonUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author Selves
 * @Date 2020/4/30
 */
@Data
public abstract class SimpleMessage<T extends SimpleMessageBody> {
    private SimpleHeader header;
    private T messageBody;

    public T getMessageBody() {
        return messageBody;
    }

    /**
     * 通过Op获取解码类信息
     *
     * @param op
     * @return
     */
    public abstract Class<T> getMessageBodyDecodeClass(int op);

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(header.getVersion());
        byteBuf.writeInt(header.getOp());
        byteBuf.writeLong(header.getId());
        byteBuf.writeBytes(GsonUtil.toJson(messageBody).getBytes());
    }

    public void decode(ByteBuf msg) {
        int version = msg.readInt();
        int op = msg.readInt();
        long id = msg.readLong();

        this.header = SimpleHeader.builder()
                .version(version)
                .op(op)
                .id(id)
                .build();

        Class<T> bodyClazz = getMessageBodyDecodeClass(op);
        T body = GsonUtil.fromJson(msg.toString(StandardCharsets.UTF_8), bodyClazz);
        this.messageBody = body;
    }
}
