package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class AddExpressionNode extends BinExpressionNode {
    @Specialization
    public int add(int leftResult, int rightResult) {
        return leftResult + rightResult;
    }
}
