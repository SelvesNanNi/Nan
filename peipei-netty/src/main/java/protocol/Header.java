package protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Selves
 * @Date 2020/4/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Header {
    /**
     * netty消息检验码：
     * 0xabef:固定值，表明该消息是netty协议消息，2字节
     * 主版本号：1~255 1字节
     * 次版本号：1~255 1字节
     */
    @Builder.Default
    private int crcCode = 0xabef0101;
    /**
     * 消息长度
     */
    private int length;
    /**
     * 会话ID
     */
    private long sessionID;
    /**
     * 消息类型
     */
    private byte type;
    /**
     * 消息优先级
     */
    private byte priority;
    /**
     * 附加信息
     */
    private Map<String,Object> attachment;


}
