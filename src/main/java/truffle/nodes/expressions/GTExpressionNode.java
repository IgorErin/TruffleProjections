package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class GTExpressionNode extends BinExpressionNode {
   @Specialization
    public boolean isGreat(int leftResult, int rightResult) {
       return leftResult > rightResult;
   }
}
