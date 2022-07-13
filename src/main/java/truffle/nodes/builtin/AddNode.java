package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class AddNode extends TFBinNode {
    @Specialization
    protected long addLong(long left, long right) {
        try {
            return Math.addExact(left, right);
        } catch (Exception e) {
            throw new RuntimeException("Long overflow in addNode");
        }
    }

    @Specialization
    protected boolean addBoolean(boolean left, boolean right) {
        return left || right;
    }
}
