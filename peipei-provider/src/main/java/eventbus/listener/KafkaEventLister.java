package eventbus.listener;

import com.google.common.eventbus.Subscribe;
import eventbus.event.api.Event;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Selves
 * @Date 2020/7/16
 */
@Slf4j
public class KafkaEventLister {

    @Subscribe
    public void listen(Event event){
        log.info("receive event message [{}]",event);
    }
}
