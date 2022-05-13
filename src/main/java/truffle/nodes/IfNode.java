package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;

public class IfNode extends FCPNode {
    @Child private FCPNode conditionNode;
    @Child private FCPNode thenNode;
    @Child private FCPNode elseNode;

    private final ConditionProfile conditionProfile = ConditionProfile.createBinaryProfile();

    public IfNode(FCPNode conditionNode, FCPNode thenNode, FCPNode elseNode) {
        this.conditionNode = conditionNode;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        if (this.conditionProfile.profile(test(this.conditionNode, frame))) {
            return this.thenNode.execute(frame);
        }

        return this.elseNode.execute(frame);
    }

    private boolean test(FCPNode node, VirtualFrame frame) {
        try {
            return node.executeInt(frame) != 0;
        } catch (UnexpectedResultException e) {
            Object result = this.conditionNode.execute(frame);
            return (int) result != 0;
        }
    }
}
