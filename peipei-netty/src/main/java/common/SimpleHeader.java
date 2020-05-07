package common;

import lombok.Builder;
import lombok.Data;

/**
 * @author Selves
 * @Date 2020/4/30
 */
@Data
@Builder
public class SimpleHeader {
    private int version = 1;
    private int op;
    private long id;
}
