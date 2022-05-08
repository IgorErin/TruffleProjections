package truffle.nodes;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "slot", type = FrameSlot.class)
public abstract class VarNode extends FCPNode {
    public abstract FrameSlot getSlot();

    public interface FrameGet<T> {
        T get(Frame frame, FrameSlot slot) throws FrameSlotTypeException;
    }

    public <T> T readUp(FrameGet<T> getter, Frame frame) throws FrameSlotTypeException {
        T value = getter.get(frame, this.getSlot());

        while (value == null) {
            frame = getLexicalScope(frame);

            if (frame == null) {
                throw new RuntimeException("unknown:" + this.getSlot().getIdentifier());
            }

            value = getter.get(frame, this.getSlot());
        }

        return value;
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected int readInt(VirtualFrame frame) throws FrameSlotTypeException {
        return this.readUp(Frame::getInt, frame);
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected boolean readBoolean(VirtualFrame frame) throws FrameSlotTypeException {
        return this.readUp(Frame::getBoolean, frame);
    }

    @Specialization(replaces = {"readInt", "readBoolean"})
    protected Object readObject(VirtualFrame frame) {
        return this.readUp(Frame::getValue, frame);
    }

    protected Frame getLexicalScope(Frame frame) {
        return (Frame) frame.getArguments()[0];
    }
}
