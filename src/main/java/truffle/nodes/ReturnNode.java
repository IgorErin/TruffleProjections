package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class ReturnNode extends FCPNode {
    @Child private FCPNode node;

    public ReturnNode(FCPNode node) {
        this.node = node;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return node.execute(frame);
    }
}
