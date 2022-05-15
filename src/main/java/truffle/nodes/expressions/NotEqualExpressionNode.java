package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class NotEqualExpressionNode extends BinExpressionNode {
    @Specialization
    public boolean notEqual(int leftResult, int rightResult) {
        return leftResult != rightResult;
    }
}
