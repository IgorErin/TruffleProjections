package truffle.nodes;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "slot", type = FrameSlot.class)
public abstract class SymbolNode extends FCPNode {
    public abstract FrameSlot getSlot();

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected int readInt(VirtualFrame frame) {
        return frame.getInt(this.getSlot());
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected Object readObject(VirtualFrame frame) {
        return frame.getObject(this.getSlot());
    }

    @Specialization(replaces = {"readInt", "readObject"})
    protected Object read(VirtualFrame frame) {
        try {
            return frame.getValue(getSlot());
        } catch (FrameSlotTypeException e) {
            System.out.println("read error");
        }

        return null;
    }
}
