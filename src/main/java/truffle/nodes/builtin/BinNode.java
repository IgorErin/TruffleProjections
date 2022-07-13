package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.NodeChild;
import truffle.nodes.TFNode;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class BinNode extends TFNode { }
