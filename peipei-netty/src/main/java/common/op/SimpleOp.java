package common.op;

import common.SimpleMessageBody;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public abstract class SimpleOp extends SimpleMessageBody {

    /**
     * 具体的业务处理逻辑
     * @return
     */
    public abstract SimpleOpResult execute();
}
