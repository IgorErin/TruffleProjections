package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class GoEExpressionNode extends BinExpressionNode {
    @Specialization
    public boolean isGreatOrEqual(int leftResult, int rightResult) {
        return leftResult >= rightResult;
    }
}
