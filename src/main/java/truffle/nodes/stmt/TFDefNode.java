package truffle.nodes.stmt;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;

@NodeChild("valueNode")
@NodeField(name = "slot", type = int.class)
public abstract class TFDefNode extends TFNode {
    protected abstract int getSlot();

    @Specialization(guards = "isLongOrIllegal(frame)")
    protected Long writeLong(VirtualFrame frame, long value) {
        frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Int);
        frame.setLong(getSlot(), value);

        return value;
    }

    @Specialization(guards = "isBooleanOrIllegal(frame)")
    protected boolean writeBoolean(VirtualFrame frame, boolean value) {
        frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Boolean);
        frame.setBoolean(getSlot(), value);

        return value;
    }

    @Specialization(replaces = {"writeLong", "writeBoolean"})
    protected Object writeObject(VirtualFrame frame, Object value) {
        System.out.println("write as Object: " + frame.getFrameDescriptor().getSlotName(getSlot()));

        frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Object);
        frame.setObject(getSlot(), value);

        return value;
    }

    public abstract Object executeWrite(VirtualFrame frame, Object value);

    protected boolean isLongOrIllegal(VirtualFrame frame) {
        final FrameSlotKind kind = frame.getFrameDescriptor().getSlotKind(getSlot());

        return kind == FrameSlotKind.Long || kind == FrameSlotKind.Illegal;
    }

    protected boolean isBooleanOrIllegal(VirtualFrame frame) {
        final FrameSlotKind kind = frame.getFrameDescriptor().getSlotKind(getSlot());

        return kind == FrameSlotKind.Boolean || kind == FrameSlotKind.Illegal;
    }
}
