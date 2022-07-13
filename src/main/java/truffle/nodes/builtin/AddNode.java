package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class AddNode extends BinNode {
    @Specialization
    protected long addInt(long value0, long value1) {
        return Math.addExact(value0, value1);
    }

    @Specialization
    protected boolean addBoolean(boolean value0, boolean value1) {
        return value0 || value1;
    }
}
