package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.VirtualFrame;

public class PrintNode extends LocalFunNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        System.out.print("Printed in truffle lang: ");
        Object[] args = getArgs(frame);

        try {
            for (Object arg : args) {
                System.out.println(arg);
            }
        } catch (Exception e) {
            throw new RuntimeException("inside PrintNode, " + e.getMessage());
        }

        return 0;
    }
}
