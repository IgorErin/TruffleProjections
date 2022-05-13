package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.types.FCPFunction;

public class LabelNode extends FCPNode {
    private FCPFunction function;

    public LabelNode(FCPFunction function) {
        this.function = function;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return function;
    }
}
