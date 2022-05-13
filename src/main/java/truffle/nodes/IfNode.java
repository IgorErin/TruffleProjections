package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;

public class IfNode extends FCPNode {
    @Child FCPNode condition;
    @Child FCPNode thenNode;
    @Child FCPNode elseNode;

    private final ConditionProfile conditionProfile = ConditionProfile.createBinaryProfile();

    public IfNode(FCPNode condition, FCPNode thenNode, FCPNode elseNode) {
        this.condition = condition;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        if (conditionProfile.profile(test(frame))) {
            return thenNode.execute(frame);
        } else {
            return elseNode.execute(frame);
        }
    }

    private boolean test(VirtualFrame frame) {
        try {
            return condition.executeBoolean(frame);
        } catch (UnexpectedResultException e) {
            return (int) condition.execute(frame) != 0;
        }
    }
}
