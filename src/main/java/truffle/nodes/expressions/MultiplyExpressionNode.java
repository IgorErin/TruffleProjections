package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class MultiplyExpressionNode extends ExpressionNode {
    @Specialization
    public int multiply(int value0, int value1) {
        return value0 * value1;
    }
}
