package truffle.nodes.stmt;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;

@NodeChild("valueNode")
@NodeField(name = "slot", type = int.class)
public abstract class TFDefNode extends TFNode { //TODO how dsl generate this ?
    protected abstract int getSlot();

    @Specialization(guards = "isIntOrIllegal(frame)")
    protected int writeInt(VirtualFrame frame, int value) {
        frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Int);
        frame.setInt(getSlot(), value);

        return value;
    }

    @Specialization(guards = "isBooleanOrIllegal(frame)")
    protected boolean writeBoolean(VirtualFrame frame, boolean value) {
        frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Boolean);
        frame.setBoolean(getSlot(), value);

        return value;
    }

    @Specialization(replaces = {"writeInt", "writeBoolean"})
    protected Object writeObject(VirtualFrame frame, Object value) {
        frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Object);
        frame.setObject(getSlot(), value);

        return value;
    }

    public abstract Object executeWrite(VirtualFrame frame, Object value);

    protected boolean isIntOrIllegal(VirtualFrame frame) {
        final FrameSlotKind kind = frame.getFrameDescriptor().getSlotKind(getSlot());

        return kind == FrameSlotKind.Int || kind == FrameSlotKind.Illegal;
    }

    protected boolean isBooleanOrIllegal(VirtualFrame frame) {
        final FrameSlotKind kind = frame.getFrameDescriptor().getSlotKind(getSlot());

        return kind == FrameSlotKind.Boolean || kind == FrameSlotKind.Illegal;
    }
}
