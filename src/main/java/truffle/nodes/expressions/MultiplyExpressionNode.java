package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;

public abstract class MultiplyExpressionNode extends BinExpressionNode {
    @Specialization
    public int multiply(int leftResult, int rightResult) {
        return leftResult * rightResult;
    }
}
