package example;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @author Selves
 * @Date 2020/4/20
 */
//--bootstrap.servers kafka0.lizhi.fm:9092 --input-topic zy_user_device_ip_topic --group.id flink_kafka_user_device_ip_01 --env test
@Slf4j
public class KafkaDemo {

    private static boolean checkArgs = false;
    private static String  SERVERS = "kafka0.lizhi.fm:9092";
    private static String INPUT_TOPIC = "zy_user_device_ip_topic";
    private static String GROUP_ID = "flink_kafka_user_device_ip_01";

    public static void main(String[] args) throws Exception {
        ParameterTool parameter = ParameterTool.fromArgs(args);
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        String env = parameter.get("env", "test");
        log.info("env={}",env);
        switch (env) {
            case "test":
                if (checkArgs && parameter.getNumberOfParameters() < 3) {
                    /**
                     * group.id单独指定，一般不可重复
                     * flink_kafka_BIZ_NAME_01
                     */
                    log.error(
                            "Missing parameters!\nUsage: --input-topic <topic> --bootstrap.servers <kafka brokers> --group.id <some id>");
                    return;
                }
                DataStream<String> dataStream = buildDataStream(parameter, environment);
                handleKafkaInput(dataStream);
                break;
            default:
                log.error("unknown env,exit");
                return;
        }

        environment.execute("kafka-demo");
    }

    private static void handleKafkaInput(DataStream<String> dataStream) {
        //just print kafka message
        log.info("开始接收数据");
        dataStream.flatMap(new RichFlatMapFunction<String, Object>() {
            @Override
            public void flatMap(String s, Collector<Object> collector) throws Exception {
                log.info("msg={}",s);
            }
        }).print();
    }

    private static DataStream<String> buildDataStream(ParameterTool parameter ,StreamExecutionEnvironment environment) {
//        String servers = parameter.getRequired("bootstrap.servers");
//        String groupId = parameter.getRequired("group.id");
//        String input_topic = parameter.getRequired("input-topic");

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers",SERVERS);
        properties.setProperty("group.id",  GROUP_ID);

        FlinkKafkaConsumer010<String> kafkaSource = new FlinkKafkaConsumer010<String>(
                INPUT_TOPIC,
                new SimpleStringSchema(),
                properties);
        return environment.addSource(kafkaSource);
    }
}
