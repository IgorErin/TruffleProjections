package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.NodeChild;
import truffle.nodes.TFNode;

@NodeChild("valueNode")
public abstract class UnNode extends TFNode { }
