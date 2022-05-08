import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;


@TypeSystemReference(FCPTypeSystem.class)
abstract class FCPNode extends Node {
     abstract  Object execute(VirtualFrame frame);

     int executeInt(VirtualFrame frame) throws UnexpectedResultException {
         return FCPTypeSystemGen.expectInteger(this.execute(frame));
     }
}


