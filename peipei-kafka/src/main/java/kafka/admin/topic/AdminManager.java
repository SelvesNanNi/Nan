package kafka.admin.topic;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author Selves
 * @Date 2020/4/15
 */
@Slf4j
public class AdminManager {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.putIfAbsent(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "kafka0.lizhi.fm:9092");
        AdminClient adminClient = AdminClient.create(properties);
        ListTopicsResult topicsResult = adminClient.listTopics();
        KafkaFuture<Set<String>> names = topicsResult.names();
        try {
            Set<String> topics = names.get();
            for (String topic : topics) {
                if(topic.contains("zy_user_device_ip_topic")){
                    log.info(topic);
                }

            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("get kafka topics error", e);
        }
    }
}
