package server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public class SimpleFrameEncoder extends LengthFieldBasedFrameDecoder {
    public SimpleFrameEncoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
