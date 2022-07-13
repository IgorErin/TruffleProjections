package truffle.nodes.stmt;

import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.nodes.TFNode;

public class IfNode extends TFNode { //TODO how dsl generate this ?
    @Child TFNode condNode;
    @Child TFNode ifNode;
    @Child TFNode elseNode;

    public IfNode(TFNode condNode, TFNode ifNode, TFNode elseNode) {
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
            throw new UnsupportedSpecializationException(this, new TFNode[] {condNode}, e);
        }
    }
}
