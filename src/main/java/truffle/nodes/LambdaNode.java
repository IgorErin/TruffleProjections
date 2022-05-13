package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.types.FCPFunction;

public class LambdaNode extends FCPNode {
    private FCPFunction function;

    public LambdaNode(FCPFunction function) {
        this.function = function;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return function;
    }
}