package truffle.builtin.arithmetic;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.builtin.LocalFunNode;

public class MinusNode extends LocalFunNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = getArgs(frame);
        long sum = 0;

        try {
            for (Object arg : args) {
                sum = Math.subtractExact(sum, (long) arg);
            }
        } catch (Exception e) {
            throw new RuntimeException("inside MinusNode, " + e.getMessage());
        }

        return sum;
    }
}