package truffle.nodes;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class InputNode extends FCPNode {
    @Specialization(rewriteOn = NumberFormatException.class)
    public int readInt(VirtualFrame frame) {
        return 4; //Integer.parseInt(System.console().readLine());
    }

    @Specialization(replaces = { "readInt" })
    public Object readObject(VirtualFrame frame) {
        return System.console().readLine();
    }
}
