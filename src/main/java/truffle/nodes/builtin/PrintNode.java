package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;

public class PrintNode extends TFNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        System.out.println("Printed in truffle lang: ");
        Object[] args = frame.getArguments();

        if (args.length > 1) {
            try {
                for (int index = 1; index < args.length; index++) {
                    System.out.println(args[index]);
                }
            } catch (Exception e) {
                throw new RuntimeException("inside PrintNode, " + e.getMessage());
            }
        }

        return null;
    }
}
