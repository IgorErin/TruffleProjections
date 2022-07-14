package truffle.nodes.builtin.arithmetic;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;
import truffle.nodes.builtin.LocalFunNode;
import truffle.parser.ArgArray;

public class AddNode extends LocalFunNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = getArgs(frame);
        long sum = 0;

        try {
            for (Object arg : args) {
                sum = Math.addExact(sum, (long) arg);
            }
        } catch (Exception e) {
            throw new RuntimeException("AddNode exception " + e.getMessage());
        }

        return sum;
    }
}
