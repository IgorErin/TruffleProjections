package truffle.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;

public class FCPRootNode extends RootNode {
    @Children private final FCPNode[] nodes;

    public FCPRootNode(FCPNode[] nodes, FrameDescriptor descriptor) { // TODO why descriptor ???
        super(null, descriptor);
        this.nodes = nodes;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
        Object result;
        int length = nodes.length;

        CompilerAsserts.compilationConstant(length);

        for (int i = 0; i < length - 1; i++) {
            nodes[i].executeGeneric(frame);
        }

        return nodes[length - 1].executeGeneric(frame);
    }
}
