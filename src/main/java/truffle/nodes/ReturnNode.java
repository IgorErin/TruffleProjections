package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class ReturnNode extends FCPNode {
    @Child private SymbolNode symbolNode;

    public ReturnNode(SymbolNode symbolNode) {
        this.symbolNode = symbolNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return symbolNode.execute(frame);
    }
}
