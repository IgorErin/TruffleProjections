package truffle.builtin.arithmetic;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.builtin.LocalFunNode;

public class MulNode extends LocalFunNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = getArgs(frame);
        long sum = 1;

        try {
            for (Object arg : args) {
                sum = Math.multiplyExact(sum, (long) arg);
            }
        } catch (Exception e) {
            throw new RuntimeException("inside MulNode, " + e.getMessage());
        }

        return sum;
    }
}
