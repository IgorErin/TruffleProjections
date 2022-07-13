package simple.nodes.exps;

import com.oracle.truffle.api.frame.FrameDescriptor;
import simple.Environment;
import simple.nodes.Node;
import truffle.nodes.TFNode;
import truffle.parser.LexicalScope;

public record BooleanNode(boolean value) implements Node {

    @Override
    public Object eval(Environment env) {
        return value;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof BooleanNode) {
            return this.value == ((BooleanNode) another).value;
        }

        return false;
    }

    @Override
    public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        return null;
    }
}
