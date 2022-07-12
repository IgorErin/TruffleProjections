package truffle.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.NodeFields;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@NodeFields(value = {
        @NodeField(name = "slot", type = int.class),
        @NodeField(name = "scope", type = MaterializedFrame.class)
})
public abstract class ReadVarNode {
    protected abstract int getSlot();
    protected abstract Frame getScope();

    @Specialization(guards = "getScope().isInt(getSlot())")
    protected int readInt(VirtualFrame frame) throws UnexpectedResultException {
        return getScope().getInt(getSlot());
    }

    @Specialization(guards = "frame.isBoolean(getSlot())")
    protected boolean readBoolean(VirtualFrame frame) {
        return getScope().getBoolean(getSlot());
    }

    @Specialization(replaces = {"readInt, readLong"}) //TODO work ?
    protected Object readObject(VirtualFrame frame) {
        if (!getScope().isObject(getSlot())) {
            CompilerDirectives.transferToInterpreter();
            Object result = getScope().getValue(getSlot());
            getScope().setObject(getSlot(), result);

            return result;
        }

        return getScope().getObject(getSlot());
    }
}
