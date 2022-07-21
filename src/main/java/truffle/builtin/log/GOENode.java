package truffle.builtin.log;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.builtin.LocalFunNode;

public class GOENode extends LocalFunNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = getArgs(frame);
        if (args.length < 2) {
            return true;
        }

        try {
            long first = (long) args[0];
            long second = (long) args[1];

            return first >= second;
        } catch (Exception e) {
            throw new RuntimeException("GNode exception " + e.getMessage());
        }
    }
}
