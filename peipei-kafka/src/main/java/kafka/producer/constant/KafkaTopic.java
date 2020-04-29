package kafka.producer.constant;

/**
 * @author Selves
 * @Date 2020/4/15
 */
public enum KafkaTopic {
    /**
     * kafka topic 枚举
     */
    STREAMDEMO("stream-demo", "流处理", true);
    private String topic;
    private String desc;
    private boolean isSendSync;

    KafkaTopic(String topic, String desc, boolean isSendSync) {
        this.topic = topic;
        this.desc = desc;
        this.isSendSync = isSendSync;
    }

    public String getTopic() {
        return topic;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isSendSync() {
        return isSendSync;
    }
}

