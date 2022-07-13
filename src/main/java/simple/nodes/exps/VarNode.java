package simple.nodes.exps;

import com.oracle.truffle.api.frame.FrameDescriptor;
import simple.Environment;
import simple.nodes.Node;
import truffle.nodes.TFNode;
import truffle.parser.LexicalScope;

import java.util.Objects;

public record VarNode(String name) implements Node {
    @Override
    public Object eval(Environment env) {
        return env.getValue(name);
    }

    @Override
    public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        return null;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof VarNode) {
            return Objects.equals(this.name, ((VarNode) another).name);
        }

        return false;
    }
}
