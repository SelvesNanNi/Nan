package kafka.producer.config;

import java.util.Properties;

/**
 * @author Selves
 * @Date 2020/4/15
 */
public class ProducerProperty {

    private static Properties props;

    private static void init(){
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    public static Properties getProducerProperty(){
        if(props != null){
            return props;
        }
        init();
        return props;
    }

}
