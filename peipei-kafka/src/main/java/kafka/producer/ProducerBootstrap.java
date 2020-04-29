package kafka.producer;

import kafka.producer.config.ProducerProperty;
import kafka.producer.constant.KafkaTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Selves
 * @Date 2020/4/15
 */
@Slf4j
public class ProducerBootstrap {

    public static void main(String[] args) {
        Properties props = ProducerProperty.getProducerProperty();
        Producer<String, String> producer = new KafkaProducer<>(props);
        KafkaTopic topic = KafkaTopic.STREAMDEMO;
        for (int i = 0; i < 10; i++) {
            String key = String.valueOf(System.currentTimeMillis());
            String val = Integer.toString(i);
            //同步等待请求结果
            if (topic.isSendSync()) {
                Future<RecordMetadata> send = producer.send(new ProducerRecord<String, String>(KafkaTopic.STREAMDEMO.getTopic()
                        , key, val));
                try {
                    RecordMetadata recordMetadata = send.get(200, TimeUnit.MILLISECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    log.error("send msg error`key={}`val={}",key,val,e);
                }
            }
            //异步等待请求结果
            else {
                Future<RecordMetadata> send = producer.send(new ProducerRecord<String, String>(KafkaTopic.STREAMDEMO.getTopic()
                        , key, val), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (null != e){
                            log.error("async send msg error ",e);
                        }
                    }
                });
            }

        }

        producer.close();
    }
}
