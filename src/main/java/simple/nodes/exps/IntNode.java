package simple.nodes.exps;

import simple.Environment;
import simple.nodes.Node;

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
}
