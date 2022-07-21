package truffle.nodes.builtin;

import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;

public class TFUnit extends TFNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        System.out.println("Warning: empty list!");

        return 0;
    }
}
