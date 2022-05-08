package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class IntNode extends FCPNode {
    int value;

    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }

    @Override
    public int executeInt(VirtualFrame frame) {
        return this.value;
    }

    @Override
    public String toString() {
        return "" + this.value;
    }
}
