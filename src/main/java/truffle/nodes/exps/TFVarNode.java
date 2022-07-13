package truffle.nodes.exps;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
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
        if (!frame.isObject(getSlot())) {
            CompilerDirectives.transferToInterpreter();
            Object result = frame.getValue(getSlot());
            frame.setObject(getSlot(), result);

            return result;
        }

        return frame.getObject(getSlot());
    }
}
