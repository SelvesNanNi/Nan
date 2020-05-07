package common;

import common.op.SimpleOp;
import common.op.SimpleOpType;
import lombok.NoArgsConstructor;

/**
 * @author Selves
 * @Date 2020/4/30
 */
@NoArgsConstructor
public class SimpleRequestMessage extends SimpleMessage<SimpleOp> {

    public SimpleRequestMessage(long id, SimpleOp simpleOp) {
        this.setHeader(
                SimpleHeader.builder()
                        .id(id).op(SimpleOpType.fromSimpleOp(simpleOp).getOp())
                        .build());
        this.setMessageBody(simpleOp);
    }

    @Override
    public Class getMessageBodyDecodeClass(int op) {
        return SimpleOpType.fromOp(op).getOpClazz();
    }
}
