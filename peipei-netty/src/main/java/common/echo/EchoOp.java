package common.echo;

import common.op.SimpleOp;
import common.op.SimpleOpResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Selves
 * @Date 2020/4/30
 */
@Slf4j
public class EchoOp extends SimpleOp {

    @Override
    public SimpleOpResult execute() {
        log.info("echo..");
        EchoOpResult echoOpResult = new EchoOpResult("reply");
        return echoOpResult;
    }
}
