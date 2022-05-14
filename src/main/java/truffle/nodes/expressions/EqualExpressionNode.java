package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class EqualExpressionNode extends BinExpressionNode {
    @Specialization
    public boolean equal(int leftResult, int rightResult) {
        return leftResult == rightResult;
    }
}
