package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class MulNode extends TFBinNode {
    @Specialization
    protected long mylInt(long left, long right) {
        try {
            return Math.multiplyExact(left, right);
        } catch (Exception e) {
            throw new RuntimeException("Long overflow in MultiplyNode");
        }
    }

    @Specialization
    protected boolean mylBoolean(boolean left, boolean right) {
        return left && right;
    }
}
