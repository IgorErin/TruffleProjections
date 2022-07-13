package truffle.types;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.RootNode;
import truffle.nodes.TFNode;
import truffle.nodes.TFRootNode;

public class TFFunction {
    public final RootCallTarget callTarget;
    private MaterializedFrame scope;

    public TFFunction(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }

    public void setScope(MaterializedFrame scope) {
        this.scope = scope;
    }

    public MaterializedFrame getScope() {
        return this.scope;
    }

    public static TFFunction createFunction(int[] slots, FrameDescriptor descriptor, TFNode bodyNode) {
        RootNode newRootNode = TFRootNode.createRootNode(slots, descriptor, bodyNode);

        return new TFFunction(Truffle.getRuntime().createCallTarget(newRootNode));// TODO deprecated API
    }
}
