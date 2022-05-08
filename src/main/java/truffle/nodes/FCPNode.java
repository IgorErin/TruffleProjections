package truffle.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import truffle.FCPTypeSystem;
import truffle.FCPTypeSystemGen;
import truffle.types.Label;

@TypeSystemReference(FCPTypeSystem.class)
public abstract class FCPNode extends Node {
     public abstract Object execute(VirtualFrame frame);

     public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
         return FCPTypeSystemGen.expectInteger(this.execute(frame));
     }

     public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
         return FCPTypeSystemGen.expectBoolean(this.execute(frame));
     }

     public Label executeLabel(VirtualFrame frame) throws UnexpectedResultException {
         return FCPTypeSystemGen.expectLabel(this.execute(frame));
     }
}
