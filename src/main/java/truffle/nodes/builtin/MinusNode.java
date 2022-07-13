package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class MinusNode extends TFBinNode {
    @Specialization
    protected long minusLong(long left, long right) {
        try {
            return Math.subtractExact(left, right);
        } catch (Exception e) {
            throw new RuntimeException("Long overflow in addNode");
        }
    }

    @Specialization
    protected boolean minusBoolean(boolean left, boolean right) {
        return left || right;
    }
}