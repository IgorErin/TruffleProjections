package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class MinusNod extends TFBinNode {
    @Specialization
    protected int minusInt(int left, int right) {
        return left - right;
    }

    @Specialization
    protected boolean minusBoolean(boolean left, boolean right) {
        return left || right;
    }
}