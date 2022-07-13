package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;

public class AddNode extends TFNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        long sum = 0;

        if (args.length > 1) {
            try {
                for (int index = 1; index < args.length; index++) {
                    sum = Math.addExact(sum, (long) args[index]);
                }
            } catch (Exception e) {
                throw new RuntimeException("inside AddNode, " + e.getMessage());
            }
        }

        return sum;
    }
}
