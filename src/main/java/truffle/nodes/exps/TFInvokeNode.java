package truffle.nodes.exps;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import simple.nodes.exps.VarNode;
import truffle.nodes.TFNode;
import truffle.parser.ArgArray;
import truffle.types.TFFunction;

public class TFInvokeNode extends TFNode {
    @Child private TFNode funNode;
    @Children private TFNode[] argNodes;
    @Child private IndirectCallNode callNode;

    public TFInvokeNode(TFNode funNode, TFNode[] argNodes) { //TODO mb indirect call ?
        this.funNode = funNode;
        this.argNodes = argNodes;
        this.callNode = Truffle.getRuntime().createIndirectCallNode();
    }

    @ExplodeLoop
    @Override
    public Object executeGeneric(VirtualFrame frame) {
            TFFunction fun = getFun(frame);

            CompilerAsserts.compilationConstant(argNodes.length);

            Object[] argValues = new Object[argNodes.length];
            for (int i = 0; i < argNodes.length; i++) {
                argValues[i] = argNodes[i].executeGeneric(frame);
            }

            DirectCallNode callNode = Truffle.getRuntime().createDirectCallNode(fun.callTarget);
            MaterializedFrame materializedFrame = (MaterializedFrame) frame.getArguments()[0];

            return callNode.call(materializedFrame, new ArgArray(argValues));
    }

    private TFFunction getFun(VirtualFrame frame) {
        try {
            System.out.println(frame.getFrameDescriptor().getSlotName(((TFVarNode) funNode).getSlot()));
            return funNode.executeFunction(frame);
        } catch (Exception e) {
            throw new RuntimeException("Inside InvokeNode, eval funNode: " + e.getMessage());
        }
    }
}
