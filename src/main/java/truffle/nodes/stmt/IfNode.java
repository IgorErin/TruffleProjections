package truffle.nodes.stmt;

import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.nodes.FCPNode;

public class IfNode extends FCPNode { //TODO how dsl generate this ?
    @Child FCPNode condNode;
    @Child FCPNode ifNode;
    @Child FCPNode elseNode;

    public IfNode(FCPNode condNode, FCPNode ifNode, FCPNode elseNode) {
        this.condNode = condNode;
        this.ifNode = ifNode;
        this.elseNode = elseNode;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        boolean result = getResult(frame);

        if (result) {
            return ifNode.executeGeneric(frame);
        }

        return elseNode;
    }

    private boolean getResult(VirtualFrame frame) {
        try {
            return condNode.executeBoolean(frame);
        } catch (UnexpectedResultException e) {
            throw new UnsupportedSpecializationException(this, new FCPNode[] {condNode}, e);
        }
    }
}
