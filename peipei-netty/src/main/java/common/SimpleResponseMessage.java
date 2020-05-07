package common;

import common.op.SimpleOpResult;
import common.op.SimpleOpType;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public class SimpleResponseMessage extends SimpleMessage<SimpleOpResult> {

    @Override
    public Class getMessageBodyDecodeClass(int op) {
        return SimpleOpType.fromOp(op).getOpResultClazz();
    }
}
