package truffle.nodes.exps;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.FrameStack;
import truffle.nodes.TFNode;


@NodeField(name = "slot", type = int.class)
public abstract class TFVarNode extends TFNode {
    protected abstract int getSlot();

    @Specialization(guards = "frame.isLong(getSlot())")
    protected long readLong(VirtualFrame frame) {
        System.out.println("reading Long: " + getName(frame));

        return frame.getLong(getSlot());
    }

    @Specialization(guards = "frame.isBoolean(getSlot())")
    protected boolean readBoolean(VirtualFrame frame) {
        System.out.println("reading Boolean: " + getName(frame));

        return frame.getBoolean(getSlot());
    }

    @Specialization(replaces = {"readLong", "readBoolean"})
    protected Object readObject(VirtualFrame frame) {
        Object value;

        if (frame.getValue(getSlot()) == frame.getFrameDescriptor().getDefaultValue()) {
            System.out.println("reading Obj with stack: " + getName(frame));

            CompilerDirectives.transferToInterpreter();

            value = findScopedVar(frame);

            return value;
        } else if (!frame.isObject(getSlot())) {
            System.out.println("reading Object as value: " + getName(frame));
            CompilerDirectives.transferToInterpreter();
            value  = frame.getValue(getSlot());
            frame.setObject(getSlot(), value);

            return value;
        } else {
            //System.out.println("reading Object: " + getName(frame));

            value = frame.getObject(getSlot());
        }

        if (value == null) {
            throw new RuntimeException("Var not bound, name: " + getName(frame));
        }

        return value;
    }

    private Object findScopedVar(VirtualFrame frame) {
        FrameStack frameStack = (FrameStack) frame.getArguments()[0];

        return frameStack.find(getSlot());
    }

    protected Object getName(VirtualFrame frame) {
        return frame.getFrameDescriptor().getSlotName(getSlot());
    }
}
