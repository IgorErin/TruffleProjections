package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class SubtractionExpressionNode extends BinExpressionNode {
    @Specialization
    public int subtraction(int leftResult, int rightResult) {
        return leftResult - rightResult;
    }
}
