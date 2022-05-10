package truffle.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;

public class FCPRootNode extends RootNode {
    FCPNode[] bodyNodes;

    public FCPRootNode(FCPNode[] bodyNodes, FrameDescriptor descriptor) {
        super(null, descriptor);
        this.bodyNodes = bodyNodes;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
        int len = bodyNodes.length - 1;

        for (int i = 0; i < len; i++) {
            this.bodyNodes[i].execute(frame);
        }

        return this.bodyNodes[len].execute(frame);
    }
}
