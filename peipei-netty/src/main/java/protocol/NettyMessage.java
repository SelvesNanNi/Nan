package protocol;

import enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Selves
 * @Date 2020/4/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class NettyMessage {
    /**
     * 消息头
     */
    private Header header;
    /**
     * 消息体
     */
    private Object body;


    public static NettyMessage buildNettyMessage(byte response, MessageType messageType){
        return NettyMessage.builder()
                .header(
                        Header.builder()
                                .type(messageType.getType())
                                .build())
                .body(response)
                .build();
    }

    public static NettyMessage buildNettyMessage(MessageType messageType){
        return NettyMessage.builder()
                .header(
                        Header.builder()
                                .type(messageType.getType())
                                .build())
                .build();
    }

}
