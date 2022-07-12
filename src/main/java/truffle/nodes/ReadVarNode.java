package truffle.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@NodeField(name = "slot", type = int.class)
public abstract class ReadVarNode {
    protected abstract int getSlot();

    @Specialization(guards = "frame.isInt(getSlot())")
    protected int readInt(VirtualFrame frame) throws UnexpectedResultException {
        return frame.getInt(getSlot());
    }

    @Specialization(guards = "frame.isBoolean(getSlot())")
    protected boolean readBoolean(VirtualFrame frame) {
        return frame.getBoolean(getSlot());
    }

    @Specialization(replaces = {"readInt, readLong"})
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
