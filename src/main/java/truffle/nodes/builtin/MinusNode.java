package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.VirtualFrame;

public class MinusNode extends TFBinNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        long sum = 0;

        if (args.length > 1) {
            try {
                for (int index = 1; index < args.length; index++) {
                    sum = Math.subtractExact(sum, (long) args[index]);
                }
            } catch (Exception e) {
                throw new RuntimeException("inside MinusNode, " + e.getMessage());
            }
        }

        return sum;
    }
}