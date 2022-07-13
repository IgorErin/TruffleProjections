package truffle.nodes.exps;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.nodes.TFNode;

public class TFBooleanNode extends TFNode {
    private final boolean value;

    public TFBooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        return this.value;
    }

    @Override
    public String toString() {
        return "BooleanNode, value = " + this.value;
    }
}
