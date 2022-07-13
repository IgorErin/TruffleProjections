package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.NodeChild;
import truffle.nodes.TFNode;

@NodeChild(value = "arguments", type = TFNode[].class)
public abstract class BuiltinNode extends TFNode {
}
