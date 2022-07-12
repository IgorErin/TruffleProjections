package truffle.types;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.RootNode;
import truffle.nodes.FCPNode;
import truffle.nodes.FCPRootNode;

public class Function {
    public final RootCallTarget callTarget;
    private MaterializedFrame scope;

    public Function(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }

    public void setScope(MaterializedFrame scope) {
        this.scope = scope;
    }

    public MaterializedFrame getScope() {
        return this.scope;
    }

    public static Function createFunction(int[] slots, FrameDescriptor descriptor, FCPNode[] nodes) {
        RootNode newRootNode = FCPRootNode.createRootNode(slots, descriptor, nodes);

        return new Function(Truffle.getRuntime().createCallTarget(newRootNode));
    }
}
