package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;

public class TimeNode extends TFNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return System.currentTimeMillis();
    }
}
