package truffle.nodes.stmt;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import truffle.nodes.TFNode;
import truffle.types.Function;

@NodeField(name = "fun", type = Function.class)
public abstract class LambdaNode extends TFNode { //TODO how dsl generate this ?
    protected abstract Function getFun();

    @Specialization(guards = "isScoped()")
    public Function getScopedFun(VirtualFrame frame) {
        return getFun();
    }

    @Specialization(replaces = {"getScopedFun"})
    public Function getFunction(VirtualFrame frame) {
        Function function = getFun();
        function.setScope(frame.materialize());

        return function;
    }

    public boolean isScoped() {
        return getFun().getScope() != null;
    }

    public Function creatFunction(RootNode rootNode) {
        return new Function(Truffle.getRuntime().createCallTarget(rootNode)); //TODO()
    }
}
