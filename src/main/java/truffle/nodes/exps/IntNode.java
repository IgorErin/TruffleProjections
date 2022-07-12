package truffle.nodes.exps;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.FCPNode;

public class IntNode extends FCPNode {
    private final int value;

    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }

    @Override
    public int executeInt(VirtualFrame frame) {
        return this.value;
    }

    @Override
    public String toString() {
        return "Int node, value = " + this.value;
    }
}
