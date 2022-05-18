package truffle.nodes;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import kotlin.collections.builders.MapBuilder;
import truffle.nodes.expressions.ExpressionNode;

@NodeField(name = "slot", type = FrameSlot.class)
public abstract class SymbolNode extends ExpressionNode {
    public abstract FrameSlot getSlot();

    private interface Lambda<T> {
        public T look(VirtualFrame frame, FrameSlot slot);
    }

    private  <T> T lookUp(Lambda<T> lambda, VirtualFrame frame) throws FrameSlotTypeException {
        T value = lambda.look(frame, this.getSlot());

        while (value == null) {
            frame = this.getLexicalScope(frame);

            if (frame == null) {
                throw new RuntimeException("Unknown variable: " + this.getSlot().getIdentifier());
            }

            value = this.lookUp(lambda, frame);
        }

        return value;
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected int readInt(VirtualFrame frame) {
        return frame.getInt(this.getSlot());
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected Object readObject(VirtualFrame frame) {
        System.out.println("Symbol node inside, read function");
        return frame.getObject(this.getSlot());
    }

    @Specialization(replaces = {"readInt", "readObject"})
    protected Object read(VirtualFrame frame) {
        try {
            System.out.println("Symbol node inside, read error");
            return frame.getValue(getSlot());
        } catch (FrameSlotTypeException e) {
            System.out.println("Symbol node inside, read error");
        }

        return null;
    }

    private VirtualFrame getLexicalScope(VirtualFrame frame) {
        return (VirtualFrame) frame.getArguments()[0];
    }
}
