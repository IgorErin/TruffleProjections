package simple.nodes.exps;

import simple.Environment;
import simple.nodes.Node;

public class IntNode implements Node {
    int number;

    public IntNode(int number) {
        this.number = number;
    }

    @Override
    public Object eval(Environment env) {
        return number;
    }
}
