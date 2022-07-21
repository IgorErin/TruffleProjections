package truffle.builtin;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;
import truffle.parser.ArgArray;

public abstract class LocalFunNode extends TFNode {
    protected Object[] getArgs(VirtualFrame frame) {
        return ((ArgArray) frame.getArguments()[1]).array();
    }
}
