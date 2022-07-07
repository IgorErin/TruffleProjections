package simple.nodes.exps;

import simple.Environment;
import simple.nodes.Node;

import java.util.Objects;

public class BooleanNode implements Node {
    boolean value;

    public BooleanNode(String value) {
        this.value = Objects.equals(value, "#t");
    }
    @Override
    public Object eval(Environment env) {
        return value;
    }
}
