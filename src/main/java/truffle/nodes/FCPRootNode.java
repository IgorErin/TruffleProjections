package truffle.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class FCPRootNode extends RootNode {
    @Children private final FCPNode[] nodes;

    public FCPRootNode(FCPNode[] nodes, FrameDescriptor descriptor) {
        super(null, descriptor);
        this.nodes = nodes;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        int lastIndex = nodes.length - 1;

        for (int i = 0; i < lastIndex; i++) {
            nodes[i].execute(frame);
        }

        return nodes[lastIndex].execute(frame);
    }
}

