package eventbus.event;

import eventbus.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Selves
 * @Date 2020/7/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaEvent implements Event {
    private String topic;
    private String msg;
    private Long timestamp;
}
