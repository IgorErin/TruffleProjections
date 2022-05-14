package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class ReturnNode extends FCPNode {
    @Child private InvokeNode invokeNode;

    public ReturnNode(InvokeNode invokeNode) {
        this.invokeNode = invokeNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return invokeNode.execute(frame);
    }
}
