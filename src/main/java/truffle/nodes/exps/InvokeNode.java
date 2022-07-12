package truffle.nodes.exps;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import truffle.nodes.FCPNode;
import truffle.types.Function;

public class InvokeNode extends FCPNode {
    @Child private FCPNode funNode;
    @Children private FCPNode[] argNodes;
    @Child private IndirectCallNode callNode;

    public InvokeNode(FCPNode funNode, FCPNode[] argNodes) {// TODO mb direct call ?
        this.funNode = funNode;
        this.argNodes = argNodes;
        this.callNode = Truffle.getRuntime().createIndirectCallNode();
    }

    @ExplodeLoop
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Function fun = getFun(frame);

        CompilerAsserts.compilationConstant(argNodes.length);

        Object[] argValues = new Object[argNodes.length];
        for (int i = 0; i < argNodes.length; i++) {
            argValues[i] = argNodes[i].executeGeneric(frame);
        }

        return callNode.call(fun.callTarget, frame, argValues);
    }

    private Function getFun(VirtualFrame frame) {
        try {
            return funNode.executeFunction(frame);
        } catch (Exception e) {
            throw new UnsupportedSpecializationException(this, new FCPNode[] {funNode}, e);
        }
    }
}
