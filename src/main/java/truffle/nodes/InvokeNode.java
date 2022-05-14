package truffle.nodes;

import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.types.FCPFunction;

public class InvokeNode extends FCPNode {
    @Child private FCPNode funNode;

    public InvokeNode(FCPNode funNode) {
        this.funNode = funNode;
    }

    private FCPFunction evalFunction(VirtualFrame frame) {
        try {
            return funNode.executeFunction(frame);
        } catch (UnexpectedResultException e) {
            throw new UnsupportedSpecializationException(this, new Node[] {this.funNode}, e);
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        FCPFunction function = this.evalFunction(frame);

        return function.callTarget.call(frame.getArguments()); //work ???
    }
}
