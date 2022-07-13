package truffle.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;
import truffle.nodes.stmt.TFDefNodeGen;

public class TFRootNode extends RootNode {
    @Children private final TFNode[] nodes;

    public TFRootNode(TFNode[] nodes, FrameDescriptor descriptor) { // TODO why descriptor ???
        super(null, descriptor);
        this.nodes = nodes;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
        int length = nodes.length;

        CompilerAsserts.compilationConstant(length);

        for (int i = 0; i < length - 1; i++) {
            nodes[i].executeGeneric(frame);
        }

        return nodes[length - 1].executeGeneric(frame);
    }

    public static RootNode createRootNode(int[] slots, FrameDescriptor descriptor, TFNode bodyNode) {
        TFNode[] bodyNodes = new TFNode[slots.length + 1];

        for (int i = 0; i < slots.length; i++) {
            bodyNodes[i] = TFDefNodeGen.create(new ReadArgNode(i), slots[i]);
        }

        bodyNodes[slots.length] = bodyNode;

        return new TFRootNode(bodyNodes, descriptor);
    }
}
