package simple.nodes.exps;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import simple.Environment;
import simple.nodes.Node;
import truffle.nodes.TFNode;
import truffle.nodes.exps.TFVarNode;
import truffle.nodes.exps.TFVarNodeGen;
import truffle.parser.LexicalScope;

import java.util.Objects;

public record VarNode(String name) implements Node {
    @Override
    public Object eval(Environment env) {
        return env.getValue(name);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof VarNode) {
            return Objects.equals(this.name, ((VarNode) another).name);
        }

        return false;
    }

    @Override
    public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        Integer frameSlot = scope.find(name);

        if (frameSlot == null) {
            frameSlot = descriptorBuilder.addSlot(FrameSlotKind.Object, name, name);
            scope.locals.put(name, frameSlot);
        }

        return TFVarNodeGen.create(frameSlot);
    }
}
