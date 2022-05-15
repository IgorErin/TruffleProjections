package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import interpreter.Environment;
import interpreter.nodes.expressions.Expression;
import org.jetbrains.annotations.NotNull;
import truffle.nodes.expressions.ExpressionNode;

public class IntNode extends ExpressionNode {
    private final int value;

    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }

    @Override
    public int executeInt(VirtualFrame frame) {
        return value;
    }

}
