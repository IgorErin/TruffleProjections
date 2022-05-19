package truffle.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeChild("valueNode")
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class DefineNode extends FCPNode {
    protected abstract FrameSlot getSlot();

    @Specialization(guards = "isInt()")
    protected int writeInt(VirtualFrame frame, int value) {
        System.out.println("in write Node, value: " + value);
        frame.setInt(this.getSlot(), value);
        return value;
    }

    @Specialization(replaces = {"writeInt"})
    protected Object writeObject(VirtualFrame frame, Object value) {
        FrameSlot slot = this.getSlot();
        System.out.println("in write Node,Generic value: " + value);

        if (slot.getKind() != FrameSlotKind.Object) {
            CompilerDirectives.transferToInterpreter();
            slot.setKind(FrameSlotKind.Object);
        }

        frame.setObject(slot, value);
        return value;
    }

    protected boolean isInt() {
        return this.getSlot().getKind() == FrameSlotKind.Int;
    }
}
