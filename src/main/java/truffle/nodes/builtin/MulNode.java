package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;

public class MulNode extends TFNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        long sum = 1;

        if (args.length > 1) {
            try {
                for (int index = 1; index < args.length; index++) {
                    sum = Math.addExact(sum, (long) args[index]);
                }
            } catch (Exception e) {
                throw new RuntimeException("inside MulNode, " + e.getMessage());
            }
        }

        return sum;
    }
}
