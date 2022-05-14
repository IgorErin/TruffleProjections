package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class NotEqualExpressionNode extends ExpressionNode {
    @Specialization
    public boolean notEqual(int value0, int value1) {
        return value0 != value1;
    }
}
