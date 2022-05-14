package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.NodeChild;
import truffle.nodes.FCPNode;

@NodeChild(value = "arguments", type = FCPNode.class)
abstract class ExpressionNode extends FCPNode {}
