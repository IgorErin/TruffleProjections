package simple.nodes.exps;

import simple.Environment;
import simple.nodes.Node;

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
}
