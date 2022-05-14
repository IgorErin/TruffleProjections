package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;

@NodeChildren({
        @NodeChild("leftNode"),
        @NodeChild("rightNode")
})
public abstract class BinExpressionNode extends ExpressionNode {}
