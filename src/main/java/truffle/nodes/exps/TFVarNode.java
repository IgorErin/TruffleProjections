package truffle.nodes.exps;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.FrameStack;
import truffle.nodes.TFNode;


@NodeField(name = "slot", type = int.class)
public abstract class TFVarNode extends TFNode {
    protected abstract int getSlot();

    @Specialization(guards = "frame.isLong(getSlot())")
    protected int readLong(VirtualFrame frame) {
        return frame.getInt(getSlot());
    }

    @Specialization(guards = "frame.isBoolean(getSlot())")
    protected boolean readBoolean(VirtualFrame frame) {
        return frame.getBoolean(getSlot());
    }

    @Specialization(replaces = {"readLong", "readBoolean"})
    protected Object readObject(VirtualFrame frame) {
        if (frame.getValue(getSlot()) == frame.getFrameDescriptor().getDefaultValue()) {
            CompilerDirectives.transferToInterpreter();

            //System.out.println("reading with stack: " + frame.getFrameDescriptor().getSlotName(getSlot()));

            Object value = findScopedVar(frame);
            //System.out.println(getSlot());
            if (value == null) {
                throw new RuntimeException("Var not bound, name: " + frame.getFrameDescriptor().getSlotName(getSlot()));
            }

            return value;
        }

        if (!frame.isObject(getSlot())) {
            CompilerDirectives.transferToInterpreter();
            Object result = frame.getValue(getSlot());
            frame.setObject(getSlot(), result);

            return result;
        }

        return frame.getObject(getSlot());
    }

    private Object findScopedVar(VirtualFrame frame) {
        FrameStack frameStack = (FrameStack) frame.getArguments()[0];

        return frameStack.find(getSlot());
    }
}
