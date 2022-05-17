package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class LTExpressionNode extends BinExpressionNode {
    @Specialization
    public boolean isLower(int leftResult, int rightResult) {
        return leftResult < rightResult;
    }
}
