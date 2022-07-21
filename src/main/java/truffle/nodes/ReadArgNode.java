package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.parser.ArgArray;

public class ReadArgNode extends TFNode {
    public final int index;

    public ReadArgNode(int index) {
        this.index= index;
    }
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args;

        try {
            args = ((ArgArray) frame.getArguments()[1]).array();
        } catch (Exception e) {
            throw new ClassCastException("Cannot cast to ArgArray in readArgNode: " + e.getMessage());
        }

        if (index < args.length) {
            return args[index];
        } else {
            throw new ArrayIndexOutOfBoundsException("Lambda args out of bound");
        }
    }
}
