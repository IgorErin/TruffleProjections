package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild("node")
public abstract class MinusExpressionNode extends ExpressionNode {
    @Specialization
    public int minus(int nodeResult) {
        return -nodeResult;
    }
}
