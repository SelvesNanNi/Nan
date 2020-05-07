package server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public class SimpleFrameDecoder extends LengthFieldPrepender {
    public SimpleFrameDecoder() {
        super(2);
    }
}
