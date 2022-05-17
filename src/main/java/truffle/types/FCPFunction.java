package truffle.types;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import truffle.nodes.FCPNode;
import truffle.nodes.FCPRootNode;

public class FCPFunction {
    public RootCallTarget callTarget;

    public FCPFunction(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }

    public static FCPFunction create(FCPNode[] nodes, FrameDescriptor descriptor) {
        return new FCPFunction(Truffle.getRuntime().createCallTarget(new FCPRootNode(nodes, descriptor)));
    }
}
