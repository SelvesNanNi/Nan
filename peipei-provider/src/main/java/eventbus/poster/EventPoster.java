package eventbus.poster;

import com.google.common.eventbus.EventBus;
import eventbus.event.KafkaEvent;
import eventbus.listener.KafkaEventLister;

/**
 * @author Selves
 * @Date 2020/7/16
 */
public class EventPoster {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus(KafkaEvent.class.getName());
        eventBus.register(new KafkaEventLister());
        eventBus.post(KafkaEvent.builder()
                .topic("peipei-provider")
                .msg("event-bus test message")
                .timestamp(System.currentTimeMillis())
                .build());
    }
}
