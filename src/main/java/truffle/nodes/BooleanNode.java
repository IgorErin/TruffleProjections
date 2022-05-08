package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class BooleanNode extends FCPNode {
    boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) {
        return value;
    }

    @Override
    public String toString() {
        return "" + this.value;
    }
}
