package truffle.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class LoEqExpressionNode extends BinExpressionNode {
    @Specialization
   public boolean isLowerOrEqual(int leftResult, int rightResult) {
       return leftResult <= rightResult;
   }
}
