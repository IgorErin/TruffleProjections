package truffle.nodes;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.types.FCPFunction;

public class InvokeNode extends FCPNode {
    @Child private FCPNode funNode;
    @Child protected IndirectCallNode callNode;

    public InvokeNode(FCPNode funNode) {
        this.funNode = funNode;
        this.callNode = Truffle.getRuntime().createIndirectCallNode();
    }

    private FCPFunction evalFunction(VirtualFrame frame) {
        try {
            return funNode.executeFunction(frame);
        } catch (UnexpectedResultException e) {
            System.out.println("Invoke node inside, catch: " + funNode);
            throw new UnsupportedSpecializationException(this, new Node[] {this.funNode}, e);
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        FCPFunction function = this.evalFunction(frame);

        return this.callNode.call(function.callTarget, frame); //work ??? TODO()
    }
}
