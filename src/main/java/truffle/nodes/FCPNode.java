package truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.nodes.types.FCPFunction;

public abstract class FCPNode extends Node {
    public abstract Object execute(VirtualFrame frame);

    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return FCPTypesGen.expectInteger(this.execute(frame));
    }

    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        return FCPTypesGen.expectBoolean(this.execute(frame));
    }

    public FCPFunction executeFunction(VirtualFrame frame) throws  UnexpectedResultException{
        return FCPTypesGen.expectFCPFunction(this.execute(frame));
    }
}
