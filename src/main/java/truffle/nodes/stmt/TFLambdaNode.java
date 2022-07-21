package truffle.nodes.stmt;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import truffle.nodes.TFNode;
import truffle.types.TFFunction;

@NodeField(name = "fun", type = TFFunction.class)
public abstract class TFLambdaNode extends TFNode {
    protected abstract TFFunction getFun();

    @Specialization(guards = "isScoped()")
    public TFFunction getScopedFun(VirtualFrame frame) {
        return getFun();
    }

    @Specialization(replaces = {"getScopedFun"})
    public TFFunction getFunction(VirtualFrame frame) {
        TFFunction function = getFun();
        function.setScope(frame.materialize());

        return function;
    }

    public boolean isScoped() {
        return getFun().getScope() != null;
    }
}
