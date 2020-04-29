package codec;

import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * @author Selves
 * @Date 2020/4/18
 */
public class MarshallingCodecFactory {

    /**
     * 创建JBoss Marshalling 编码器
     *
     * @return
     */
    public static Marshaller buildMarshallingEncoder() throws IOException {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        return marshallerFactory.createMarshaller(configuration);
    }

    /**
     * 创建JBoss Marshalling 解码器
     *
     * @return
     */
    public static Unmarshaller buildMarshallingDecoder() throws IOException {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        return marshallerFactory.createUnmarshaller(configuration);
    }
}
