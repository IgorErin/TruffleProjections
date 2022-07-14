package truffle;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class FrameStack {
    private final MaterializedFrame frame;
    private final FrameStack outer;

    public FrameStack(MaterializedFrame frame, FrameStack outer) {
        this.frame = frame;
        this.outer = outer;
    }

    public Object find(int slot) {
        Object value = frame.getValue(slot);

        if (value != null) {
            return value;
        } else if (outer != null) {
            return outer.find(slot);
        } else {
            return null;
        }
    }
}
