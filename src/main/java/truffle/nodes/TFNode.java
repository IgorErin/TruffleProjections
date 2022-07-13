package truffle.nodes;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.types.Function;
import truffle.types.TypesGen;

public abstract class TFNode extends Node {
    public abstract Object executeGeneric(VirtualFrame frame);

    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        return TypesGen.expectBoolean(executeGeneric(frame));
    }

    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return TypesGen.expectInteger(executeGeneric(frame));
    }

    public String executeString(VirtualFrame frame) throws UnexpectedResultException {
        return TypesGen.expectString(executeGeneric(frame));
    }

    public Function executeFunction(VirtualFrame frame) throws UnexpectedResultException {
        return TypesGen.expectFunction(executeGeneric(frame));
    }
}
