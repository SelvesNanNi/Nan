package common.op;

import common.echo.EchoOp;
import common.echo.EchoOpResult;

import java.util.function.Predicate;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public enum SimpleOpType {
    /**
     * 应答消息
     */
    ECHO(1, EchoOp.class, EchoOpResult.class)
    ;
    private int op;
    private Class<? extends SimpleOp> opClazz;
    private Class<? extends SimpleOpResult> opResultClazz;

    SimpleOpType(int op, Class<? extends SimpleOp> opClazz, Class<? extends SimpleOpResult> opResultClazz) {
        this.op = op;
        this.opClazz = opClazz;
        this.opResultClazz = opResultClazz;
    }

    public static SimpleOpType fromOp(int op){
        return getSimpleOpType(requestType -> requestType.op == op);
    }

    public static SimpleOpType fromSimpleOp(SimpleOp simpleOp){
        return getSimpleOpType(requestType -> requestType.opClazz == simpleOp.getClass());
    }

    private static SimpleOpType getSimpleOpType(Predicate<SimpleOpType> predicate){
        for (SimpleOpType simpleOpType : values()) {
            if(predicate.test(simpleOpType)){
                return simpleOpType;
            }
        }
        throw new AssertionError("not found "+SimpleOpType.class.getSimpleName());
    }

    public int getOp() {
        return op;
    }

    public Class<? extends SimpleOp> getOpClazz() {
        return opClazz;
    }

    public Class<? extends SimpleOpResult> getOpResultClazz() {
        return opResultClazz;
    }
}
