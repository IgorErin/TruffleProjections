package simple.nodes.exps;

import simple.Environment;
import simple.nodes.Node;

public record VarNode(String name) implements Node {
    @Override
    public Object eval(Environment env) {
        return env.getValue(name);
    }
}
