package truffle.nodes;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

public class ReadVarNode extends FCPNode {
    FrameSlot slot;

    @Override
    public Object execute(VirtualFrame frame) {
        return frame.getValue(slot);
    }

    @Override
    public int executeInt(VirtualFrame frame) {
        return frame.getInt(slot);
    }
}
