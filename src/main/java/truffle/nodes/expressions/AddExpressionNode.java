package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;
import truffle.nodes.FCPNode;

public abstract class AddExpressionNode extends FCPNode {
    @Specialization
    public int add(int value0, int value1) {
        return value0 + value1;
    }
}
