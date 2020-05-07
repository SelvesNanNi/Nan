package common.echo;

import common.op.SimpleOpResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Selves
 * @Date 2020/4/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EchoOpResult extends SimpleOpResult {
    private String msg;
}
