package simple.nodes.exps;

import com.oracle.truffle.api.frame.FrameDescriptor;
import simple.Environment;
import simple.nodes.Node;
import truffle.nodes.TFNode;
import truffle.nodes.exps.TFIntNode;
import truffle.parser.LexicalScope;

public record IntNode(int number) implements Node {
    @Override
    public Object eval(Environment env) {
        return number;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof IntNode) {
            return this.number == ((IntNode) another).number();
        }

        return false;
    }

    @Override
    public TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope) {
        return new TFIntNode(number);
    }
}
