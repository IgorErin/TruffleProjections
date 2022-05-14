package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class EqualExpressionNode extends ExpressionNode {
    @Specialization
    public boolean equal(int value0, int value1) {
        return value0 == value1;
    }
}
