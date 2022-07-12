package truffle.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;
import truffle.nodes.stmt.DefNodeGen;

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

    public static RootNode createRootNode(int[] slots, FrameDescriptor descriptor, FCPNode[] nodes) {
        FCPNode[] bodyNodes = new FCPNode[slots.length + nodes.length];

        for (int i = 0; i < slots.length; i++) {
            bodyNodes[i] = DefNodeGen.create(new ReadArgNode(i), slots[i]);
        }

        System.arraycopy(nodes, 0, bodyNodes, slots.length, nodes.length);

        return new FCPRootNode(bodyNodes, descriptor);
    }
}
