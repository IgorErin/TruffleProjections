package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.NodeChild;
import truffle.nodes.TFNode;

@NodeChild(value = "left", type = TFNode.class)
@NodeChild(value = "right", type = TFNode.class)
public abstract class TFBinNode extends TFNode { }
