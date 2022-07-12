package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class ReadArgNode extends FCPNode {
    public final int index;

    public ReadArgNode(int index) {
        this.index= index;
    }
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = frame.getArguments();

        if (index < args.length - 1) {
            return args[index + 1];
        } else {
            throw new ArrayIndexOutOfBoundsException("Lambda args out of bound");
        }
    }
}
