package enums;

import protocol.NettyMessage;

/**
 * @author Selves
 * @Date 2020/4/18
 */
public enum MessageType {
    /**
     * 消息类型枚举
     * todo
     * 0-业务请求消息
     * 1-业务响应消息
     * 2-业务ONE WAY （既是请求又是响应消息）
     */
    LOGIN_REQ((byte) 3, "握手请求"),
    LOGIN_RESP((byte) 4, "握手应答"),
    HEARTBEAT_REQ((byte) 5, "心跳请求"),
    HEARTBEAT_RESP((byte) 6, "心跳应答"),

    ;
    private byte type;
    private String desc;

    MessageType(byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean checkMessageType(NettyMessage message, MessageType messageType) {
        return message.getHeader() != null &&
                message.getHeader().getType() == messageType.getType();
    }
}
