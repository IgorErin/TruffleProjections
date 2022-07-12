package truffle.nodes.exps;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.FCPNode;

public class StringNode extends FCPNode {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }


    @Override
    public String executeString(VirtualFrame frame) {
        return this.value;
    }

    @Override
    public String toString() {
        return "String node, value = " + this.value;
    }
}
