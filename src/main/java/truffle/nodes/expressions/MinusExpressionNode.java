package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class MinusExpressionNode extends ExpressionNode {
    @Specialization
    public int minus(int value0) {
        return -value0;
    }
}
