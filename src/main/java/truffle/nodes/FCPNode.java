package truffle.nodes;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class FCPNode extends Node {
    public abstract Object executeGeneric(VirtualFrame frame);

}
