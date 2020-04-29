package kafka.admin.topic;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Selves
 * @Date 2020/4/15
 */
@Slf4j
public class TopicManager {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        for (String kafkaTopic : getKafkaTopics(Optional.empty())) {
            log.info(kafkaTopic);
        }
    }

    public static List<String> getKafkaTopics(Optional<String> zkUrl){
        List<String> topics = null;
        try {
            ZooKeeper zk = new ZooKeeper(
                    zkUrl.orElse("kafka0.lizhi.fm:9092"),10000,null);
            topics = zk.getChildren("/brokers/topics", false);
        } catch (KeeperException | InterruptedException | IOException e) {
            log.error("get kafka topics error`zkUrl={}",zkUrl.orElse("localhost:2181"),e);
        }
        return topics;
    }
}
